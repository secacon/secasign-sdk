package com.secacon.secasign.sdk.dto.document;

import java.util.List;

public class DocumentSearchResultDto {

    private List<ReadDocumentDto> documents;

    private Integer totalDocuments;

    private Integer totalPages;

    private Integer page;

    private Integer size;

    public DocumentSearchResultDto() {
    }

    public DocumentSearchResultDto(List<ReadDocumentDto> documents, Integer totalDocuments, Integer totalPages, Integer page, Integer size) {
        this.documents = documents;
        this.totalDocuments = totalDocuments;
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;
    }

    public List<ReadDocumentDto> getDocuments() {
        return documents;
    }

    public Integer getTotalDocuments() {
        return totalDocuments;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
