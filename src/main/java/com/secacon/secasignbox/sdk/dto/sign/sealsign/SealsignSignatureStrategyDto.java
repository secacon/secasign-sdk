package com.secacon.secasignbox.sdk.dto.sign.sealsign;

import com.secacon.secasignbox.sdk.dto.sign.general.SignatureStrategyDto;
import com.secacon.secasignbox.sdk.dto.sign.general.SignatureStrategyTypeDto;

public class SealsignSignatureStrategyDto implements SignatureStrategyDto {

    private SignatureStrategyTypeDto type;

    private String encodedGraphic;

    private String signatureDescription;

    private SealsignSignatureAppearanceDto signatureAppearance;

    private SealsignSignatureRenderingDto signatureRendering;

    private String signerName;

    private String signatureReason;

    private String signatureLocation;

    private String signatureContact;

    private Integer signaturePreservationSize;

    private String signatureFieldName;

    private Integer signaturePage;

    private Integer signatureLeft;

    private Integer signatureTop;

    private Integer signatureWidth;

    private Integer signatureHeight;

    private String signatureFont;

    private Integer signatureFontSize;

    private Integer signatureFontRgb1;

    private Integer signatureFontRgb2;

    private Integer signatureFontRgb3;

    public SealsignSignatureStrategyDto() {
    }

    public SealsignSignatureStrategyDto(SignatureStrategyTypeDto type, String encodedGraphic, String signatureDescription, SealsignSignatureAppearanceDto signatureAppearance, SealsignSignatureRenderingDto signatureRendering, String signerName, String signatureReason, String signatureLocation, String signatureContact, Integer signaturePreservationSize, String signatureFieldName, Integer signaturePage, Integer signatureLeft, Integer signatureTop, Integer signatureWidth, Integer signatureHeight, String signatureFont, Integer signatureFontSize, Integer signatureFontRgb1, Integer signatureFontRgb2, Integer signatureFontRgb3) {
        this.type = type;
        this.encodedGraphic = encodedGraphic;
        this.signatureDescription = signatureDescription;
        this.signatureAppearance = signatureAppearance;
        this.signatureRendering = signatureRendering;
        this.signerName = signerName;
        this.signatureReason = signatureReason;
        this.signatureLocation = signatureLocation;
        this.signatureContact = signatureContact;
        this.signaturePreservationSize = signaturePreservationSize;
        this.signatureFieldName = signatureFieldName;
        this.signaturePage = signaturePage;
        this.signatureLeft = signatureLeft;
        this.signatureTop = signatureTop;
        this.signatureWidth = signatureWidth;
        this.signatureHeight = signatureHeight;
        this.signatureFont = signatureFont;
        this.signatureFontSize = signatureFontSize;
        this.signatureFontRgb1 = signatureFontRgb1;
        this.signatureFontRgb2 = signatureFontRgb2;
        this.signatureFontRgb3 = signatureFontRgb3;
    }

    @Override
    public SignatureStrategyTypeDto type() {
        return type;
    }

    public SignatureStrategyTypeDto getType() {
        return type;
    }

    public void setType(SignatureStrategyTypeDto type) {
        this.type = type;
    }

    public String getEncodedGraphic() {
        return encodedGraphic;
    }

    public void setEncodedGraphic(String encodedGraphic) {
        this.encodedGraphic = encodedGraphic;
    }

    public String getSignatureDescription() {
        return signatureDescription;
    }

    public void setSignatureDescription(String signatureDescription) {
        this.signatureDescription = signatureDescription;
    }

    public SealsignSignatureAppearanceDto getSignatureAppearance() {
        return signatureAppearance;
    }

    public void setSignatureAppearance(SealsignSignatureAppearanceDto signatureAppearance) {
        this.signatureAppearance = signatureAppearance;
    }

    public SealsignSignatureRenderingDto getSignatureRendering() {
        return signatureRendering;
    }

    public void setSignatureRendering(SealsignSignatureRenderingDto signatureRendering) {
        this.signatureRendering = signatureRendering;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
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

    public String getSignatureContact() {
        return signatureContact;
    }

    public void setSignatureContact(String signatureContact) {
        this.signatureContact = signatureContact;
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

    public Integer getSignaturePage() {
        return signaturePage;
    }

    public void setSignaturePage(Integer signaturePage) {
        this.signaturePage = signaturePage;
    }

    public Integer getSignatureLeft() {
        return signatureLeft;
    }

    public void setSignatureLeft(Integer signatureLeft) {
        this.signatureLeft = signatureLeft;
    }

    public Integer getSignatureTop() {
        return signatureTop;
    }

    public void setSignatureTop(Integer signatureTop) {
        this.signatureTop = signatureTop;
    }

    public Integer getSignatureWidth() {
        return signatureWidth;
    }

    public void setSignatureWidth(Integer signatureWidth) {
        this.signatureWidth = signatureWidth;
    }

    public Integer getSignatureHeight() {
        return signatureHeight;
    }

    public void setSignatureHeight(Integer signatureHeight) {
        this.signatureHeight = signatureHeight;
    }

    public String getSignatureFont() {
        return signatureFont;
    }

    public void setSignatureFont(String signatureFont) {
        this.signatureFont = signatureFont;
    }

    public Integer getSignatureFontSize() {
        return signatureFontSize;
    }

    public void setSignatureFontSize(Integer signatureFontSize) {
        this.signatureFontSize = signatureFontSize;
    }

    public Integer getSignatureFontRgb1() {
        return signatureFontRgb1;
    }

    public void setSignatureFontRgb1(Integer signatureFontRgb1) {
        this.signatureFontRgb1 = signatureFontRgb1;
    }

    public Integer getSignatureFontRgb2() {
        return signatureFontRgb2;
    }

    public void setSignatureFontRgb2(Integer signatureFontRgb2) {
        this.signatureFontRgb2 = signatureFontRgb2;
    }

    public Integer getSignatureFontRgb3() {
        return signatureFontRgb3;
    }

    public void setSignatureFontRgb3(Integer signatureFontRgb3) {
        this.signatureFontRgb3 = signatureFontRgb3;
    }
}
