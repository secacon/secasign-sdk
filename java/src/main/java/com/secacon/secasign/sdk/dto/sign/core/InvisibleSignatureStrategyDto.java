package com.secacon.secasign.sdk.dto.sign.core;

public class InvisibleSignatureStrategyDto implements SignatureStrategyDto {

    private SignatureStrategyTypeDto type;

    private String signerName;

    private String signatureContact;

    private String signatureReason;

    private String signatureLocation;

    private Integer signaturePreservationSize;

    private String signatureFieldName;

    public InvisibleSignatureStrategyDto() {
    }

    public InvisibleSignatureStrategyDto(SignatureStrategyTypeDto type, String signerName, String signatureContact, String signatureReason, String signatureLocation, Integer signaturePreservationSize, String signatureFieldName) {
        this.type = type;
        this.signerName = signerName;
        this.signatureContact = signatureContact;
        this.signatureReason = signatureReason;
        this.signatureLocation = signatureLocation;
        this.signaturePreservationSize = signaturePreservationSize;
        this.signatureFieldName = signatureFieldName;
    }

    public SignatureStrategyTypeDto getType() {
        return type;
    }

    public void setType(SignatureStrategyTypeDto type) {
        this.type = type;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getSignatureContact() {
        return signatureContact;
    }

    public void setSignatureContact(String signatureContact) {
        this.signatureContact = signatureContact;
    }

    public String getSignatureReason() {
        return signatureReason;
    }

    public void setSignatureReason(String signatureReason) {
        this.signatureReason = signatureReason;
    }

    public String getSignatureLocation() {
        return signatureLocation;
    }

    public void setSignatureLocation(String signatureLocation) {
        this.signatureLocation = signatureLocation;
    }

    public Integer getSignaturePreservationSize() {
        return signaturePreservationSize;
    }

    public void setSignaturePreservationSize(Integer signaturePreservationSize) {
        this.signaturePreservationSize = signaturePreservationSize;
    }

    public String getSignatureFieldName() {
        return signatureFieldName;
    }

    public void setSignatureFieldName(String signatureFieldName) {
        this.signatureFieldName = signatureFieldName;
    }

    @Override
    public SignatureStrategyTypeDto type() {
        return type;
    }
}
