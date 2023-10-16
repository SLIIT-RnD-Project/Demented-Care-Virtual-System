""" Upload an audio file using postman and predict the emotion. """
from flask import Flask, jsonify, request
import pandas as pd
import numpy as np
import os
import seaborn as sns
import matplotlib.pyplot as plt
import librosa
import librosa.display
import warnings
from tensorflow.keras.models import load_model

warnings.filterwarnings('ignore')

app = Flask(__name__)

model = load_model('trained_model.h5')

def extract_mfcc(data, sr):
    mfcc = np.mean(librosa.feature.mfcc(y=data, sr=sr, n_mfcc=40).T, axis=0)
    return mfcc

@app.route('/', methods=['POST'])
def predict_emotion():
    # Check if the 'audio' file is present in the request
    if 'audio' not in request.files:
        return jsonify({'error': 'No audio file uploaded'})
    
    audio_file = request.files['audio']
    
    # Save the uploaded audio file
    audio_path = 'uploaded.wav'
    audio_file.save(audio_path)
    
    # Load the audio file
    data, sampling_rate = librosa.load(audio_path)
    
    # Preprocess the audio data
    mfcc = extract_mfcc(data, sampling_rate)
    
    # Reshape the MFCCs to match the input shape of the model
    mfcc = np.expand_dims(mfcc, axis=0)
    mfcc = np.expand_dims(mfcc, axis=-1)
    
    # Pass the MFCCs through the model
    prediction = model.predict(mfcc)
    
    # Interpret the model's prediction
    emotion_labels = ['angry', 'disgusting', 'fear', 'happy', 'neutral', 'ps', 'sad']
    predicted_emotion = emotion_labels[np.argmax(prediction)]
    
    return jsonify({'predicted_emotion': predicted_emotion})

if __name__ == '__main__':
    app.run(debug=True)
