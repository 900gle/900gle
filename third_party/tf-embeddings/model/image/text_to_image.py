import torch
from diffusers import StableDiffusionPipeline

# Stable Diffusion 모델 로드
model_id = "CompVis/stable-diffusion-v1-4"
pipe = StableDiffusionPipeline.from_pretrained(model_id, torch_dtype=torch.float16)
pipe = pipe.to("cuda")

# 텍스트 입력
prompt = "a beautiful landscape with mountains and a river"

# 이미지 생성
with torch.autocast("cuda"):
    image = pipe(prompt).images[0]

# 이미지 저장
image.save("generated_image.png")
