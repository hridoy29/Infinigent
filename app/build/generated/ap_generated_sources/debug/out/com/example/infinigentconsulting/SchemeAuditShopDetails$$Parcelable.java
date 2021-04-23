
package com.example.infinigentconsulting;

import java.util.Date;
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
public class SchemeAuditShopDetails$$Parcelable
    implements Parcelable, ParcelWrapper<com.example.infinigentconsulting.SchemeAuditShopDetails>
{

    private com.example.infinigentconsulting.SchemeAuditShopDetails schemeAuditShopDetails$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<SchemeAuditShopDetails$$Parcelable>CREATOR = new Creator<SchemeAuditShopDetails$$Parcelable>() {


        @Override
        public SchemeAuditShopDetails$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new SchemeAuditShopDetails$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public SchemeAuditShopDetails$$Parcelable[] newArray(int size) {
            return new SchemeAuditShopDetails$$Parcelable[size] ;
        }

    }
    ;

    public SchemeAuditShopDetails$$Parcelable(com.example.infinigentconsulting.SchemeAuditShopDetails schemeAuditShopDetails$$2) {
        schemeAuditShopDetails$$0 = schemeAuditShopDetails$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(schemeAuditShopDetails$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.example.infinigentconsulting.SchemeAuditShopDetails schemeAuditShopDetails$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(schemeAuditShopDetails$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(schemeAuditShopDetails$$1));
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .MobileNumber);
            parcel$$1 .writeSerializable(schemeAuditShopDetails$$1 .VisitedDate);
            parcel$$1 .writeInt(schemeAuditShopDetails$$1 .AicId);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .Number);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .OutlateTypeId);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .RetailSellerName);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .DistributorName);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .OutlateName);
            parcel$$1 .writeInt(schemeAuditShopDetails$$1 .GccCode);
            parcel$$1 .writeString(schemeAuditShopDetails$$1 .OutlateAddress);
            parcel$$1 .writeInt(schemeAuditShopDetails$$1 .Id);
            parcel$$1 .writeInt(schemeAuditShopDetails$$1 .AsmId);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.example.infinigentconsulting.SchemeAuditShopDetails getParcel() {
        return schemeAuditShopDetails$$0;
    }

    public static com.example.infinigentconsulting.SchemeAuditShopDetails read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.example.infinigentconsulting.SchemeAuditShopDetails schemeAuditShopDetails$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            schemeAuditShopDetails$$4 = new com.example.infinigentconsulting.SchemeAuditShopDetails();
            identityMap$$1 .put(reservation$$0, schemeAuditShopDetails$$4);
            schemeAuditShopDetails$$4 .MobileNumber = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .VisitedDate = ((Date) parcel$$3 .readSerializable());
            schemeAuditShopDetails$$4 .AicId = parcel$$3 .readInt();
            schemeAuditShopDetails$$4 .Number = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .OutlateTypeId = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .RetailSellerName = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .DistributorName = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .OutlateName = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .GccCode = parcel$$3 .readInt();
            schemeAuditShopDetails$$4 .OutlateAddress = parcel$$3 .readString();
            schemeAuditShopDetails$$4 .Id = parcel$$3 .readInt();
            schemeAuditShopDetails$$4 .AsmId = parcel$$3 .readInt();
            com.example.infinigentconsulting.SchemeAuditShopDetails schemeAuditShopDetails$$3 = schemeAuditShopDetails$$4;
            identityMap$$1 .put(identity$$1, schemeAuditShopDetails$$3);
            return schemeAuditShopDetails$$3;
        }
    }

}
