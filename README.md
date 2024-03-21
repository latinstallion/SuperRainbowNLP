SuperRainbowNLP
==========

SuperRainbowNLP (RNLP) is a versatile tool for several natural language processing activities. These activities include supervised name entity recognition and relationship extraction.


Configuration
==========
SuperRainbowNLP (RNLP) is based on Hibernate and can be configured easily to use different data sources (for example, mysql). Hibernate settings are in hibernate.cfg.xml. Please ensure to set connection.url, connection.username and connection.password correctly. Other application settings are in configuration.conf. Make sure the paths are updated and they exist.

The following are instructions on how to use RNLP for different applications.

Relationship Extraction
==========
For relationship extraction, the system needs to first load the text document and the entities annotations.

1. Load test/train documents into the framework using 'SimpleDocumentLoader' or create a new document loader by implementing the 'IDocumentAnalyzer' interface (for test/train sets).

2. Load annotations by creating instances of 'Phrase' and 'PhraseLink' and ensure to save them with HibernateUtil (for test/train sets).

3. Create machine learning examples by creating Phrase/PhraseLink and MLExample objects (for test/train sets)

4. Calculate features for every machine learning example (MLExample objects)

5. Train a machine learning model with train examples.

6. Evaluate the model using test examples.

This is an example of temporal relationship extraction implementation used for I2B2 shared task submission (https://www.i2b2.org/NLP/TemporalRelations/) :
https://github.com/latinstallion/temporal-relation.git


How to cite in your publication?
==========
Ple