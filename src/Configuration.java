import jdk.nashorn.internal.runtime.regexp.joni.Config;

/**
 * Created by test on 2016/11/16.
 */
public class Configuration {
    public String configType;

    public Configuration () {

    }

    public Configuration (String path, String configType) {
        this.configType = configType;
    }

}
