package https;


import config.HttpUrlConfig;

/**
 * Created by Administrator on 2015/11/3.
 */
public class DevelopUrlAddress extends HttpadstractUrladdress {

    public DevelopUrlAddress(HttpUrlConfig.Cotrol_Type cotrol_type) {
        super(cotrol_type);
    }

    @Override
    public String getUrl() {
        String str="";
        switch (cotrol_type)
        {
            case read:
                str=HttpUrlConfig.url_develop_read;
                break;
            case write:
                str=HttpUrlConfig.url_develop_write;
                break;
        }
        return str;
    }
}
