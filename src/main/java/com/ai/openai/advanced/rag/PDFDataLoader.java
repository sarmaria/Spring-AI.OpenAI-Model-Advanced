package com.ai.openai.advanced.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PDFDataLoader {

    private final VectorStore vectorStore;

    @Value("classpath:/Eazybytes_HR_Policies.pdf")
    private Resource pdfData;

    public PDFDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPDFInToVectorStore(){
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(pdfData);
        List<Document> docs = tikaDocumentReader.get();
        vectorStore.add(docs);
    }
}
