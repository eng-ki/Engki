SET var= pottedplant skateboard diningtable parkingmeter hairdrier teddybear tennisracket hotdog

for %%a in (%var%) do (
    python eval.py --trained_model=weights/yolact_base_54_800000.pth --score_threshold=0.8 --top_k=3 --display_bboxes=false --display_text=true --display_scores=false --mask_order=0 --images=inputs/%%a:outputs/%%a

python eval.py --trained_model=weights/yolact_base_54_800000.pth --score_threshold=0.8 --top_k=3 --display_bboxes=false --display_text=true --display_scores=false --mask_order=1 --images=inputs/%%a:outputs/%%a

python eval.py --trained_model=weights/yolact_base_54_800000.pth --score_threshold=0.8 --top_k=3 --display_bboxes=false --display_text=true --display_scores=false --mask_order=2 --images=inputs/%%a:outputs/%%a
)
