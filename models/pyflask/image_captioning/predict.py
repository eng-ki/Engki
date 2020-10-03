import torch
import matplotlib.pyplot as plt
import numpy as np
import argparse
import pickle
import os
import csv
from torchvision import transforms

from image_captioning.build_vocab import Vocabulary
from image_captioning import *
from image_captioning.model import EncoderCNN, DecoderRNN
from PIL import Image

# Device configuration
device = torch.device('cpu')


def load_image(image_path, transform=None):
    image = Image.open(image_path).convert('RGB')
    image = image.resize([224, 224], Image.LANCZOS)

    if transform is not None:
        image = transform(image).unsqueeze(0)

    return image


def main(image_path):
    parser = argparse.ArgumentParser()

    parser.add_argument('--encoder_path', type=str,
                        default='./image_captioning/models/encoder-20-3000.ckpt',
                        help='path for trained encoder')
    parser.add_argument('--decoder_path', type=str,
                        default='./image_captioning/models/decoder-20-3000.ckpt',
                        help='path for trained decoder')
    parser.add_argument('--vocab_path', type=str, default='./image_captioning/data/vocab.pkl', help='path for vocabulary wrapper')

    # Model parameters (should be same as paramters in train.py)
    parser.add_argument('--embed_size', type=int, default=256, help='dimension of word embedding vectors')
    parser.add_argument('--hidden_size', type=int, default=512, help='dimension of lstm hidden states')
    parser.add_argument('--num_layers', type=int, default=1, help='number of layers in lstm')
    args = parser.parse_args()

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
    map_location = 'cpu'
    encoder.load_state_dict(torch.load(args.encoder_path, map_location=map_location))
    decoder.load_state_dict(torch.load(args.decoder_path, map_location=map_location))

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

    image = load_image(image_path, transform)
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

    if 'carrot' in sentence:
        found_words.remove('car')

    return sentence[8:-6], found_words


