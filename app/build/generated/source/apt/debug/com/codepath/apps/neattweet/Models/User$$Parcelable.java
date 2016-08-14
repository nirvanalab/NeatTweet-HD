
package com.codepath.apps.neattweet.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-08-14T15:10-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class User$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.neattweet.Models.User>
{

    private com.codepath.apps.neattweet.Models.User user$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static User$$Parcelable.Creator$$0 CREATOR = new User$$Parcelable.Creator$$0();

    public User$$Parcelable(com.codepath.apps.neattweet.Models.User user$$2) {
        user$$0 = user$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(user$$0, parcel$$0, flags, new HashSet<Integer>());
    }

    public static void write(com.codepath.apps.neattweet.Models.User user$$1, android.os.Parcel parcel$$1, int flags$$0, Set<Integer> identitySet$$0) {
        int identity$$0 = System.identityHashCode(user$$1);
        parcel$$1 .writeInt(identity$$0);
        if (!identitySet$$0 .contains(identity$$0)) {
            identitySet$$0 .add(identity$$0);
            if (user$$1 == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeString(user$$1 .statusesCount);
                parcel$$1 .writeString(user$$1 .profileBackgroundImageUrl);
                parcel$$1 .writeString(user$$1 .name);
                parcel$$1 .writeString(user$$1 .profileBackgroundColor);
                parcel$$1 .writeString(user$$1 .description);
                parcel$$1 .writeString(user$$1 .location);
                parcel$$1 .writeString(user$$1 .friendsCount);
                parcel$$1 .writeString(user$$1 .screenName);
                parcel$$1 .writeString(user$$1 .id);
                parcel$$1 .writeString(user$$1 .followersCount);
                parcel$$1 .writeString(user$$1 .profileImageUrl);
                parcel$$1 .writeString(user$$1 .profileTextColour);
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.neattweet.Models.User getParcel() {
        return user$$0;
    }

    public static com.codepath.apps.neattweet.Models.User read(android.os.Parcel parcel$$3, Map<Integer, Object> identityMap$$0) {
        com.codepath.apps.neattweet.Models.User user$$3;
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$0 .containsKey(identity$$1)) {
            com.codepath.apps.neattweet.Models.User user$$4 = ((com.codepath.apps.neattweet.Models.User) identityMap$$0 .get(identity$$1));
            if ((user$$4 == null)&&(identity$$1 != 0)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return user$$4;
        }
        if (parcel$$3 .readInt() == -1) {
            user$$3 = null;
            identityMap$$0 .put(identity$$1, null);
        } else {
            com.codepath.apps.neattweet.Models.User user$$5;
            identityMap$$0 .put(identity$$1, null);
            user$$5 = new com.codepath.apps.neattweet.Models.User();
            identityMap$$0 .put(identity$$1, user$$5);
            user$$5 .statusesCount = parcel$$3 .readString();
            user$$5 .profileBackgroundImageUrl = parcel$$3 .readString();
            user$$5 .name = parcel$$3 .readString();
            user$$5 .profileBackgroundColor = parcel$$3 .readString();
            user$$5 .description = parcel$$3 .readString();
            user$$5 .location = parcel$$3 .readString();
            user$$5 .friendsCount = parcel$$3 .readString();
            user$$5 .screenName = parcel$$3 .readString();
            user$$5 .id = parcel$$3 .readString();
            user$$5 .followersCount = parcel$$3 .readString();
            user$$5 .profileImageUrl = parcel$$3 .readString();
            user$$5 .profileTextColour = parcel$$3 .readString();
            user$$3 = user$$5;
        }
        return user$$3;
    }

    public final static class Creator$$0
        implements Creator<User$$Parcelable>
    {


        @Override
        public User$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new User$$Parcelable(read(parcel$$2, new HashMap<Integer, Object>()));
        }

        @Override
        public User$$Parcelable[] newArray(int size) {
            return new User$$Parcelable[size] ;
        }

    }

}
