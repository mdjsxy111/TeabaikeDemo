package com.estyle.teabaike.constant;

public class Url {

    public static final String BASE_URL = "http://sns.maimaicha.com/";

    public static final String[] TITLES = new String[]{"头条", "百科", "咨询", "经营", "数据"};
    public static final int[] TYPES = new int[]{0, 16, 52, 53, 54};

    // 频道接口
    public static final String CHANNEL_URL = "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&row=15";
    // 频道类型
    public static final String TYPE = "type";
    // 分页
    public static final String PAGE = "page";


    //首页幻灯片数据路径
    public static final String HEADLINE_HEADER_URL = "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";

    //头条数据
    public static final String HEADLINE_URL = "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines";

    //内容详情页，id为对应文章id，主界面Json数据中获取
    public static final String CONTENT_URL = "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent";
    public static final String ID = "id";

    //搜索
    public static final String SEARCH_URL = "api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&rows=10";
    public static final String SEARCH = "search";


}
