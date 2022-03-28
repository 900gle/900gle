ARG ELK_VERSION

# https://www.docker.elastic.co/
FROM docker.elastic.co/elasticsearch/elasticsearch:${ELK_VERSION}

# Add your elasticsearch plugins setup here
# Example: RUN elasticsearch-plugin install analysis-icu
COPY config/stopFilter.txt config/stopFilter.txt
COPY config/synonymsFilter.txt config/synonymsFilter.txt
COPY config/user_dictionary.txt config/user_dictionary.txt
COPY config/doo-plugin-7.15.1.zip config/doo-plugin-7.15.1.zip
COPY config/kr-danalyzer-7.15.1.zip config/kr-danalyzer-7.15.1.zip


RUN elasticsearch-plugin install analysis-nori
RUN elasticsearch-plugin install file:///usr/share/elasticsearch/config/doo-plugin-7.15.1.zip
RUN elasticsearch-plugin install file:///usr/share/elasticsearch/config/kr-danalyzer-7.15.1.zip
