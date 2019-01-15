package rest.responseTypes;

import java.util.List;

public class WrongPosts {
    public Boolean b;
    public List<AdditionalData> additionalData;

    public class AdditionalData {
        public Integer id;
        public String name;
    }
}

