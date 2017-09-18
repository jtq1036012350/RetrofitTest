package winning.retrofittest;

import android.provider.MediaStore;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Jiang on 2016/12/7.
 */

public class ReturnBean implements Serializable {

    public Boolean error;
    public List<Results> results;

    public class Results implements Serializable {

        public String createdAt;
        public String publishedAt;
        public String _id;
        public String source;
        public Boolean used;
        public String type;
        public String url;
        public String desc;
        public String who;

        @Override
        public String toString() {
            String s = "";
            Field[] arr = this.getClass().getFields();
            for (Field f : getClass().getFields()) {
                try {
                    s += f.getName() + "=" + f.get(this) + "\n,";
                } catch (Exception e) {
                }
            }
            return getClass().getSimpleName() + "[" + (arr.length == 0 ? s : s.substring(0, s.length() - 1)) + "]";
        }
    }

    @Override
    public String toString() {
        String s = "";
        Field[] arr = this.getClass().getFields();
        for (Field f : getClass().getFields()) {
            try {
                s += f.getName() + "=" + f.get(this) + "\n,";
            } catch (Exception e) {
            }
        }
        return getClass().getSimpleName() + "[" + (arr.length == 0 ? s : s.substring(0, s.length() - 1)) + "]";
    }
}
