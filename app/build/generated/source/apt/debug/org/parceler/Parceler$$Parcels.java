
package org.parceler;

import java.util.HashMap;
import java.util.Map;
import com.codepath.apps.neattweet.Models.User;
import com.codepath.apps.neattweet.Models.User$$Parcelable;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-08-07T17:58-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Parceler$$Parcels
    implements Repository<org.parceler.Parcels.ParcelableFactory>
{

    private final Map<Class, org.parceler.Parcels.ParcelableFactory> map$$0 = new HashMap<Class, org.parceler.Parcels.ParcelableFactory>();

    public Parceler$$Parcels() {
        map$$0 .put(User.class, new Parceler$$Parcels.User$$Parcelable$$0());
    }

    public Map<Class, org.parceler.Parcels.ParcelableFactory> get() {
        return map$$0;
    }

    private final static class User$$Parcelable$$0
        implements org.parceler.Parcels.ParcelableFactory<User>
    {


        @Override
        public User$$Parcelable buildParcelable(User input) {
            return new User$$Parcelable(input);
        }

    }

}
