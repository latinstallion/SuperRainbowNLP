package rainbownlp.machinelearning;

import java.util.ArrayList;
import java.util.List;

import rainbownlp.machinelearning.convertor.WekaFormatConvertor;
import rainbownlp.util.ConfigurationUtil;
import rainbownlp.util.HibernateUtil;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper