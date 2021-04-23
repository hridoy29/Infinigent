
package com.example.infinigentconsulting;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class SchemeAuditParent$$Parcelable
    implements Parcelable, ParcelWrapper<com.example.infinigentconsulting.SchemeAuditParent>
{

    private com.example.infinigentconsulting.SchemeAuditParent schemeAuditParent$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<SchemeAuditParent$$Parcelable>CREATOR = new Creator<SchemeAuditParent$$Parcelable>() {


        @Override
        public SchemeAuditParent$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new SchemeAuditParent$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public SchemeAuditParent$$Parcelable[] newArray(int size) {
            return new SchemeAuditParent$$Parcelable[size] ;
        }

    }
    ;

    public SchemeAuditParent$$Parcelable(com.example.infinigentconsulting.SchemeAuditParent schemeAuditParent$$2) {
        schemeAuditParent$$0 = schemeAuditParent$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(schemeAuditParent$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.example.infinigentconsulting.SchemeAuditParent schemeAuditParent$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(schemeAuditParent$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(schemeAuditParent$$1));
            parcel$$1 .writeSerializable(schemeAuditParent$$1 .CreationDate);
            parcel$$1 .writeInt(schemeAuditParent$$1 .WillGetAnyDiscountOfferFromDistributor);
            parcel$$1 .writeString(schemeAuditParent$$1 .SallesOfficerVisitingDay);
            parcel$$1 .writeInt(schemeAuditParent$$1 .ChallanTypeId);
            parcel$$1 .writeString(schemeAuditParent$$1 .Number);
            parcel$$1 .writeSerializable(schemeAuditParent$$1 .ModificationDate);
            parcel$$1 .writeString(schemeAuditParent$$1 .DateOfScheme);
            parcel$$1 .writeString(schemeAuditParent$$1 .CommentDetails);
            parcel$$1 .writeInt(schemeAuditParent$$1 .IsFacilitatedByScheme);
            parcel$$1 .writeString(schemeAuditParent$$1 .LatestChallanDate);
            parcel$$1 .writeString(schemeAuditParent$$1 .DoesGotAnyChallan);
            parcel$$1 .writeInt(schemeAuditParent$$1 .ModifierId);
            parcel$$1 .writeInt(schemeAuditParent$$1 .DoesExpiredProductAvailable);
            parcel$$1 .writeString(schemeAuditParent$$1 .SchemeDetails);
            parcel$$1 .writeInt(schemeAuditParent$$1 .Comments);
            parcel$$1 .writeInt(schemeAuditParent$$1 .CreatorId);
            parcel$$1 .writeInt(schemeAuditParent$$1 .IsWrittenRecordAvailable);
            parcel$$1 .writeInt(schemeAuditParent$$1 .DoesGotLatestDiscountOffer);
            parcel$$1 .writeInt(schemeAuditParent$$1 .DoesCocaColaLabelAvailable);
            parcel$$1 .writeInt(schemeAuditParent$$1 .CommentsType);
            parcel$$1 .writeSerializable(schemeAuditParent$$1 .Date);
            parcel$$1 .writeInt(schemeAuditParent$$1 .DoesSatisfiedWithSallesOfficer);
            parcel$$1 .writeInt(schemeAuditParent$$1 .IsGccCodeAvailable);
            parcel$$1 .writeInt(schemeAuditParent$$1 .UserId);
            parcel$$1 .writeString(schemeAuditParent$$1 .ChallanAmount);
            parcel$$1 .writeString(schemeAuditParent$$1 .OutlateName);
            parcel$$1 .writeInt(schemeAuditParent$$1 .Id);
            parcel$$1 .writeInt(schemeAuditParent$$1 .IsKnowenAboutScheme);
            parcel$$1 .writeString(schemeAuditParent$$1 .SchemeMediaTypeId);
            parcel$$1 .writeInt(schemeAuditParent$$1 .DoesSatisfiedWithProductOrderAndService);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.example.infinigentconsulting.SchemeAuditParent getParcel() {
        return schemeAuditParent$$0;
    }

    public static com.example.infinigentconsulting.SchemeAuditParent read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.example.infinigentconsulting.SchemeAuditParent schemeAuditParent$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            schemeAuditParent$$4 = new com.example.infinigentconsulting.SchemeAuditParent();
            identityMap$$1 .put(reservation$$0, schemeAuditParent$$4);
            schemeAuditParent$$4 .CreationDate = ((java.util.Date) parcel$$3 .readSerializable());
            schemeAuditParent$$4 .WillGetAnyDiscountOfferFromDistributor = parcel$$3 .readInt();
            schemeAuditParent$$4 .SallesOfficerVisitingDay = parcel$$3 .readString();
            schemeAuditParent$$4 .ChallanTypeId = parcel$$3 .readInt();
            schemeAuditParent$$4 .Number = parcel$$3 .readString();
            schemeAuditParent$$4 .ModificationDate = ((java.util.Date) parcel$$3 .readSerializable());
            schemeAuditParent$$4 .DateOfScheme = parcel$$3 .readString();
            schemeAuditParent$$4 .CommentDetails = parcel$$3 .readString();
            schemeAuditParent$$4 .IsFacilitatedByScheme = parcel$$3 .readInt();
            schemeAuditParent$$4 .LatestChallanDate = parcel$$3 .readString();
            schemeAuditParent$$4 .DoesGotAnyChallan = parcel$$3 .readString();
            schemeAuditParent$$4 .ModifierId = parcel$$3 .readInt();
            schemeAuditParent$$4 .DoesExpiredProductAvailable = parcel$$3 .readInt();
            schemeAuditParent$$4 .SchemeDetails = parcel$$3 .readString();
            schemeAuditParent$$4 .Comments = parcel$$3 .readInt();
            schemeAuditParent$$4 .CreatorId = parcel$$3 .readInt();
            schemeAuditParent$$4 .IsWrittenRecordAvailable = parcel$$3 .readInt();
            schemeAuditParent$$4 .DoesGotLatestDiscountOffer = parcel$$3 .readInt();
            schemeAuditParent$$4 .DoesCocaColaLabelAvailable = parcel$$3 .readInt();
            schemeAuditParent$$4 .CommentsType = parcel$$3 .readInt();
            schemeAuditParent$$4 .Date = ((java.util.Date) parcel$$3 .readSerializable());
            schemeAuditParent$$4 .DoesSatisfiedWithSallesOfficer = parcel$$3 .readInt();
            schemeAuditParent$$4 .IsGccCodeAvailable = parcel$$3 .readInt();
            schemeAuditParent$$4 .UserId = parcel$$3 .readInt();
            schemeAuditParent$$4 .ChallanAmount = parcel$$3 .readString();
            schemeAuditParent$$4 .OutlateName = parcel$$3 .readString();
            schemeAuditParent$$4 .Id = parcel$$3 .readInt();
            schemeAuditParent$$4 .IsKnowenAboutScheme = parcel$$3 .readInt();
            schemeAuditParent$$4 .SchemeMediaTypeId = parcel$$3 .readString();
            schemeAuditParent$$4 .DoesSatisfiedWithProductOrderAndService = parcel$$3 .readInt();
            com.example.infinigentconsulting.SchemeAuditParent schemeAuditParent$$3 = schemeAuditParent$$4;
            identityMap$$1 .put(identity$$1, schemeAuditParent$$3);
            return schemeAuditParent$$3;
        }
    }

}
