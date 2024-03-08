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

1. Load test/train documents into the 