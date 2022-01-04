import pickle
import sys
from sklearn.feature_extraction.text import CountVectorizer, TfidfTransformer

if __name__ == '__main__':
    file = open(sys.argv[2]+"/die_svm.pickle", "rb")
    svm_model = pickle.load(file)
    file.close()


    fileVocab = open(sys.argv[2]+"/die_svm_vocab.pickle", "rb")
    vocab = pickle.load(fileVocab)
    fileVocab.close()

    s=sys.argv[1]
    input=[s] #需要把输入传进来
    count_v1= CountVectorizer(vocabulary=vocab)
    test = count_v1.fit_transform(input)
    tfidftransformer = TfidfTransformer()
    test_data = tfidftransformer.fit(test).transform(test)

#     pred= svm_model.predict(test_data)
#     print(pred)
    pred_proba = svm_model.predict_proba(test_data)
    print(pred_proba)
