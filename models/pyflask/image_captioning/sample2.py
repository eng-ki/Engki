import torch
import matplotlib.pyplot as plt
import numpy as np 
import argparse
import pickle 
import os
import csv
from torchvision import transforms 
from build_vocab import Vocabulary
from model import EncoderCNN, DecoderRNN
from PIL import Image


# Device configuration
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

def load_image(image_path, transform=None):
    image = Image.open(image_path).convert('RGB')
    image = image.resize([224, 224], Image.LANCZOS)
    
    if transform is not None:
        image = transform(image).unsqueeze(0)
    
    return image

def main(args):
    # Image preprocessing
    transform = transforms.Compose([
        transforms.ToTensor(), 
        transforms.Normalize((0.485, 0.456, 0.406), 
                             (0.229, 0.224, 0.225))])
    
    # Load vocabulary wrapper
    with open(args.vocab_path, 'rb') as f:
        vocab = pickle.load(f)

    # Build models
    encoder = EncoderCNN(args.embed_size).eval()  # eval mode (batchnorm uses moving mean/variance)
    decoder = DecoderRNN(args.embed_size, args.hidden_size, len(vocab), args.num_layers)
    encoder = encoder.to(device)
    decoder = decoder.to(device)

    # Load the trained model parameters
    encoder.load_state_dict(torch.load(args.encoder_path))
    decoder.load_state_dict(torch.load(args.decoder_path))

    words = ['person',
             'bicycle',
             'car',
             'motorcycle',
             'airplane',
             'bus',
             'train',
             'truck',
             'boat',
             'traffic light',
             'fire hydrant',
             'stop sign',
             'parking meter',
             'bench',
             'bird',
             'cat',
             'dog',
             'horse',
             'sheep',
             'cow',
             'elephant',
             'bear',
             'zebra',
             'giraffe',
             'backpack',
             'umbrella',
             'handbag',
             'tie',
             'suitcase',
             'frisbee',
             'skis',
             'snowboard',
             'sports ball',
             'kite',
             'baseball bat',
             'baseball glove',
             'skateboard',
             'surfboard',
             'tennis racket',
             'bottle',
             'wine glass',
             'cup',
             'fork',
             'knife',
             'spoon',
             'bowl',
             'banana',
             'apple',
             'sandwich',
             'orange',
             'broccoli',
             'carrot',
             'hot dog',
             'pizza',
             'donut',
             'cake',
             'chair',
             'couch',
             'potted plant',
             'bed',
             'dining table',
             'toilet',
             'tv',
             'laptop',
             'mouse',
             'remote',
             'keyboard',
             'cell phone',
             'microwave',
             'oven',
             'toaster',
             'sink',
             'refrigerator',
             'book',
             'clock',
             'vase',
             'scissors',
             'teddy bear',
             'hair drier',
             'toothbrush']

    # Prepare images
    if args.images:

        # inputs path
        input_path = os.listdir(args.images)
        sentences = []

        # folders in inputs
        for path in input_path:
            file_path = args.images+path+'/'
            if os.path.isdir(file_path):
                files = os.listdir(file_path)
                # files in folders
                for file in files:

                    image = load_image(file_path+file, transform)
                    image_tensor = image.to(device)

                    # Generate an caption from the image
                    feature = encoder(image_tensor)
                    sampled_ids = decoder.sample(feature)
                    sampled_ids = sampled_ids[0].cpu().numpy()  # (1, max_seq_length) -> (max_seq_length)

                    # Convert word_ids to words
                    sampled_caption = []
                    for word_id in sampled_ids:
                        word = vocab.idx2word[word_id]
                        sampled_caption.append(word)
                        if word == '<end>':
                            break
                    caption = ' '.join(sampled_caption)[8:-6]
                    sentences.append(caption)
                    for word in words:
                        if word in caption:
                            f = open('captions2.csv', 'a', encoding='utf-8', newline="")
                            writer = csv.writer(f)
                            writer.writerow([file_path+file, word, caption])
                            f.close()
    
    # Print out the image and the generated caption
    #     for s in sentences:
    #         print(s)

    # Prepare an image
    else:
        image = load_image(args.image, transform)
        image_tensor = image.to(device)

        found_words = []

        # Generate an caption from the image
        feature = encoder(image_tensor)
        sampled_ids = decoder.sample(feature)
        sampled_ids = sampled_ids[0].cpu().numpy()  # (1, max_seq_length) -> (max_seq_length)

        # Convert word_ids to words
        sampled_caption = []
        for word_id in sampled_ids:
            word = vocab.idx2word[word_id]
            sampled_caption.append(word)
            if word == '<end>':
                break
        sentence = ' '.join(sampled_caption)

        for word in words:
            if word in sentence:
                found_words.append(word)

        if 'hot dog' in sentence:
            found_words.remove('dog')

        return sentence, found_words
        # Print out the image and the generated caption
        # print(sentence)

    
if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--image', type=str, help='input image for generating caption')
    parser.add_argument('--images', type=str, help='input image directory for generating caption')
    parser.add_argument('--encoder_path', type=str, default='models/encoder-5-3000.pkl', help='path for trained encoder')
    parser.add_argument('--decoder_path', type=str, default='models/decoder-5-3000.pkl', help='path for trained decoder')
    parser.add_argument('--vocab_path', type=str, default='data/vocab.pkl', help='path for vocabulary wrapper')
    
    # Model parameters (should be same as paramters in train.py)
    parser.add_argument('--embed_size', type=int , default=256, help='dimension of word embedding vectors')
    parser.add_argument('--hidden_size', type=int , default=512, help='dimension of lstm hidden states')
    parser.add_argument('--num_layers', type=int , default=1, help='number of layers in lstm')
    args = parser.parse_args()
    main(args)
