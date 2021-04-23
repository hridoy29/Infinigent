package com.example.infinigentconsulting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
@Parcel
public class SchemeAuditParent {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("Number")
    @Expose
    public String Number;
    @SerializedName("UserId")
    @Expose
    public int UserId;
    @SerializedName("OutlateName")
    @Expose
    public String OutlateName;
    @SerializedName("Date")
    @Expose
    public Date	Date;
    @SerializedName("IsKnowenAboutScheme")
    @Expose
    public int IsKnowenAboutScheme;
    @SerializedName("SchemeDetails")
    @Expose
    public String SchemeDetails;
    @SerializedName("SchemeMediaTypeId")
    @Expose
    public String SchemeMediaTypeId;
    @SerializedName("IsFacilitatedByScheme")
    @Expose
    public int IsFacilitatedByScheme;
    @SerializedName("DateOfScheme")
    @Expose
    public String DateOfScheme;
    @SerializedName("IsWrittenRecordAvailable")
    @Expose
    public int IsWrittenRecordAvailable;
    @SerializedName("LatestChallanDate")
    @Expose
    public String LatestChallanDate;
    @SerializedName("ChallanAmount")
    @Expose
    public String ChallanAmount	;
    @SerializedName("DoesGotAnyChallan")
    @Expose
    public String DoesGotAnyChallan;
    @SerializedName("ChallanTypeId")
    @Expose
    public int ChallanTypeId;
    @SerializedName("DoesExpiredProductAvailable")
    @Expose
    public int DoesExpiredProductAvailable;
    @SerializedName("DoesSatisfiedWithSallesOfficer")
    @Expose
    public int DoesSatisfiedWithSallesOfficer;
    @SerializedName("DoesSatisfiedWithProductOrderAndService")
    @Expose
    public int DoesSatisfiedWithProductOrderAndService;
    @SerializedName("SallesOfficerVisitingDay")
    @Expose
    public String SallesOfficerVisitingDay;
    @SerializedName("DoesGotLatestDiscountOffer")
    @Expose
    public int DoesGotLatestDiscountOffer;
    @SerializedName("WillGetAnyDiscountOfferFromDistributor")
    @Expose
    public int WillGetAnyDiscountOfferFromDistributor;
    @SerializedName("DoesCocaColaLabelAvailable")
    @Expose
    public int DoesCocaColaLabelAvailable;
    @SerializedName("IsGccCodeAvailable")
    @Expose
    public int IsGccCodeAvailable;
    @SerializedName("CommentsType")
    @Expose
    public int CommentsType;
    @SerializedName("Comments")
    @Expose
    public int Comments;
    @SerializedName("CommentDetails")
    @Expose
    public String CommentDetails;
    @SerializedName("CreatorId")
    @Expose
    public int CreatorId;
    @SerializedName("CreationDate")
    @Expose
    public Date CreationDate;
    @SerializedName("ModifierId")
    @Expose
    public int ModifierId;
    @SerializedName("ModificationDate")
    @Expose
    public Date ModificationDate;

    public SchemeAuditParent() {
    }

    public SchemeAuditParent(int id, String number, int userId, String outlateName, Date Date, int isKnowenAboutScheme, String schemeDetails, String schemeMediaTypeId, int isFacilitatedByScheme, String dateOfScheme, int isWrittenRecordAvailable, String latestChallanDate, String challanAmount, String doesGotAnyChallan, int challanTypeId, int doesExpiredProductAvailable, int doesSatisfiedWithSallesOfficer, int doesSatisfiedWithProductOrderAndService, String sallesOfficerVisitingDay, int doesGotLatestDiscountOffer, int willGetAnyDiscountOfferFromDistributor, int doesCocaColaLabelAvailable, int isGccCodeAvailable, int commentsType, int comments, String commentDetails, int creatorId, Date creationDate, int modifierId, Date modificationDate) {
        this.Id = id;
        this.Number = number;
        this.UserId = userId;
        this.OutlateName = outlateName;
        this.Date = Date;
        this.IsKnowenAboutScheme = isKnowenAboutScheme;
        this.SchemeDetails = schemeDetails;
        this.SchemeMediaTypeId = schemeMediaTypeId;
        this.IsFacilitatedByScheme = isFacilitatedByScheme;
        this.DateOfScheme = dateOfScheme;
        this.IsWrittenRecordAvailable = isWrittenRecordAvailable;
        this.LatestChallanDate = latestChallanDate;
        this.ChallanAmount = challanAmount;
        this.DoesGotAnyChallan = doesGotAnyChallan;
        this.ChallanTypeId = challanTypeId;
        this.DoesExpiredProductAvailable = doesExpiredProductAvailable;
        this.DoesSatisfiedWithSallesOfficer = doesSatisfiedWithSallesOfficer;
        this.DoesSatisfiedWithProductOrderAndService = doesSatisfiedWithProductOrderAndService;
        this.SallesOfficerVisitingDay = sallesOfficerVisitingDay;
        this.DoesGotLatestDiscountOffer = doesGotLatestDiscountOffer;
        this.WillGetAnyDiscountOfferFromDistributor = willGetAnyDiscountOfferFromDistributor;
        this.DoesCocaColaLabelAvailable = doesCocaColaLabelAvailable;
        this.IsGccCodeAvailable = isGccCodeAvailable;
        this.CommentsType = commentsType;
        this.Comments = comments;
        this.CommentDetails = commentDetails;
        this.CreatorId = creatorId;
        this.CreationDate = creationDate;
        this.ModifierId = modifierId;
        this.ModificationDate = modificationDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public String getOutlateName() {
        return OutlateName;
    }

    public void setOutlateName(String outlateName) {
        this.OutlateName = outlateName;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public int getIsKnowenAboutScheme() {
        return IsKnowenAboutScheme;
    }

    public void setIsKnowenAboutScheme(int isKnowenAboutScheme) {
        this.IsKnowenAboutScheme = isKnowenAboutScheme;
    }

    public String getSchemeDetails() {
        return SchemeDetails;
    }

    public void setSchemeDetails(String schemeDetails) {
        this.SchemeDetails = schemeDetails;
    }

    public String getSchemeMediaTypeId() {
        return SchemeMediaTypeId;
    }

    public void setSchemeMediaTypeId(String schemeMediaTypeId) {
        this.SchemeMediaTypeId = schemeMediaTypeId;
    }

    public int getIsFacilitatedByScheme() {
        return IsFacilitatedByScheme;
    }

    public void setIsFacilitatedByScheme(int isFacilitatedByScheme) {
        this.IsFacilitatedByScheme = isFacilitatedByScheme;
    }

    public String getDateOfScheme() {
        return DateOfScheme;
    }

    public void setDateOfScheme(String dateOfScheme) {
        this.DateOfScheme = dateOfScheme;
    }

    public int getIsWrittenRecordAvailable() {
        return IsWrittenRecordAvailable;
    }

    public void setIsWrittenRecordAvailable(int isWrittenRecordAvailable) {
        this.IsWrittenRecordAvailable = isWrittenRecordAvailable;
    }

    public String getLatestChallanDate() {
        return LatestChallanDate;
    }

    public void setLatestChallanDate(String latestChallanDate) {
        this.LatestChallanDate = latestChallanDate;
    }

    public String getChallanAmount() {
        return ChallanAmount;
    }

    public void setChallanAmount(String challanAmount) {
        this.ChallanAmount = challanAmount;
    }

    public String getDoesGotAnyChallan() {
        return DoesGotAnyChallan;
    }

    public void setDoesGotAnyChallan(String doesGotAnyChallan) {
        this.DoesGotAnyChallan = doesGotAnyChallan;
    }

    public int getChallanTypeId() {
        return ChallanTypeId;
    }

    public void setChallanTypeId(int challanTypeId) {
        this.ChallanTypeId = challanTypeId;
    }

    public int getDoesExpiredProductAvailable() {
        return DoesExpiredProductAvailable;
    }

    public void setDoesExpiredProductAvailable(int doesExpiredProductAvailable) {
        this.DoesExpiredProductAvailable = doesExpiredProductAvailable;
    }

    public int getDoesSatisfiedWithSallesOfficer() {
        return DoesSatisfiedWithSallesOfficer;
    }

    public void setDoesSatisfiedWithSallesOfficer(int doesSatisfiedWithSallesOfficer) {
        this.DoesSatisfiedWithSallesOfficer = doesSatisfiedWithSallesOfficer;
    }

    public int getDoesSatisfiedWithProductOrderAndService() {
        return DoesSatisfiedWithProductOrderAndService;
    }

    public void setDoesSatisfiedWithProductOrderAndService(int doesSatisfiedWithProductOrderAndService) {
        this.DoesSatisfiedWithProductOrderAndService = doesSatisfiedWithProductOrderAndService;
    }

    public String getSallesOfficerVisitingDay() {
        return SallesOfficerVisitingDay;
    }

    public void setSallesOfficerVisitingDay(String sallesOfficerVisitingDay) {
        this.SallesOfficerVisitingDay = sallesOfficerVisitingDay;
    }

    public int getDoesGotLatestDiscountOffer() {
        return DoesGotLatestDiscountOffer;
    }

    public void setDoesGotLatestDiscountOffer(int doesGotLatestDiscountOffer) {
        this.DoesGotLatestDiscountOffer = doesGotLatestDiscountOffer;
    }

    public int getWillGetAnyDiscountOfferFromDistributor() {
        return WillGetAnyDiscountOfferFromDistributor;
    }

    public void setWillGetAnyDiscountOfferFromDistributor(int willGetAnyDiscountOfferFromDistributor) {
        this.WillGetAnyDiscountOfferFromDistributor = willGetAnyDiscountOfferFromDistributor;
    }

    public int getDoesCocaColaLabelAvailable() {
        return DoesCocaColaLabelAvailable;
    }

    public void setDoesCocaColaLabelAvailable(int doesCocaColaLabelAvailable) {
        this.DoesCocaColaLabelAvailable = doesCocaColaLabelAvailable;
    }

    public int getIsGccCodeAvailable() {
        return IsGccCodeAvailable;
    }

    public void setIsGccCodeAvailable(int isGccCodeAvailable) {
        this.IsGccCodeAvailable = isGccCodeAvailable;
    }

    public int getCommentsType() {
        return CommentsType;
    }

    public void setCommentsType(int commentsType) {
        this.CommentsType = commentsType;
    }

    public int getComments() {
        return Comments;
    }

    public void setComments(int comments) {
        this.Comments = comments;
    }

    public String getCommentDetails() {
        return CommentDetails;
    }

    public void setCommentDetails(String commentDetails) {
        this.CommentDetails = commentDetails;
    }

    public int getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(int creatorId) {
        this.CreatorId = creatorId;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.CreationDate = creationDate;
    }

    public int getModifierId() {
        return ModifierId;
    }

    public void setModifierId(int modifierId) {
        this.ModifierId = modifierId;
    }

    public Date getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.ModificationDate = modificationDate;
    }



}
