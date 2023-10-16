from flask import Flask, request, jsonify
import cv2
import numpy as np
from keras.models import model_from_json

app = Flask(__name__)

emotion_dict = {0: "Angry", 1: "Disgusted", 2: "Fearful",
                3: "Happy", 4: "Neutral", 5: "Sad", 6: "Surprised"}

# Load emotion detection model
json_file = open('model/emotion_model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
emotion_model = model_from_json(loaded_model_json)
emotion_model.load_weights("model/emotion_model.h5")
print("Loaded model from disk")


def detect_emotions(video_file):
    cap = cv2.VideoCapture(video_file)
    result = []

    while True:
        ret, frame = cap.read()
        if not ret:
            break
        face_detector = cv2.CascadeClassifier(
            'haarcascades/haarcascade_frontalface_default.xml')
        gray_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        num_faces = face_detector.detectMultiScale(
            gray_frame, scaleFactor=1.3, minNeighbors=5)

        for (x, y, w, h) in num_faces:
            roi_gray_frame = gray_frame[y:y + h, x:x + w]
            cropped_img = np.expand_dims(np.expand_dims(
                cv2.resize(roi_gray_frame, (48, 48)), -1), 0)
            emotion_prediction = emotion_model.predict(cropped_img)
            maxindex = int(np.argmax(emotion_prediction))
            result.append(emotion_dict[maxindex])
            result = list(set(result))

    cap.release()
    cv2.destroyAllWindows()

    return result


@app.route('/', methods=['POST'])
def upload_file():
    if request.method == 'POST':
        file = request.files['file']
        if file:
            file_path = 'uploads/uploaded_video.mp4'
            file.save(file_path)
            result = detect_emotions(file_path)
            return jsonify(result=result)
    return "No file uploaded."


if __name__ == '__main__':
    app.run(debug=True)
