import tensorflow as tf
from transformers import BertTokenizer, TFBertModel
from sentence_transformers import SentenceTransformer

# 모델과 토크나이저 로드
model_name = "bert-base-multilingual-cased"
tokenizer = BertTokenizer.from_pretrained(model_name)
model = TFBertModel.from_pretrained(model_name)

# 한국어 텍스트 입력
texts = ["안녕하세요", "오늘 날씨가 좋네요"]

# 토크나이징
inputs = tokenizer(texts, return_tensors='tf', padding=True, truncation=True)

# 임베딩 생성
outputs = model(**inputs)

# 임베딩 결과
embeddings = outputs.last_hidden_state
print(embeddings)


#########

# Sentence-BERT 모델 로드
model = SentenceTransformer('sentence-transformers/xlm-r-100langs-bert-base-nli-stsb-mean-tokens')

# 한국어 텍스트 입력
texts = ["안녕하세요", "오늘 날씨가 좋네요"]

# 임베딩 생성
embeddings = model.encode(texts)
print(embeddings)