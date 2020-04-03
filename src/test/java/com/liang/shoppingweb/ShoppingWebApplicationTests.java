package com.liang.shoppingweb;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.entity.cart.CartShopVo;
import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.entity.common.Tag;
import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.entity.user.Favourite;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.cart.CartShopVoMapper;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
import com.liang.shoppingweb.mapper.common.DictionaryMapper;
import com.liang.shoppingweb.mapper.common.TagMapper;
import com.liang.shoppingweb.mapper.enterprise.EnterpriseMapper;
import com.liang.shoppingweb.mapper.shop.ShopItemMapper;
import com.liang.shoppingweb.mapper.shop.ShopMapper;
import com.liang.shoppingweb.mapper.user.FavouriteMapper;
import com.liang.shoppingweb.mapper.user.UserMapper;
import com.liang.shoppingweb.service.common.DictionaryService;
import com.liang.shoppingweb.service.common.TagService;
import com.liang.shoppingweb.service.order.OrderService;
import com.liang.shoppingweb.service.order.OrderVoService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoppingWebApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Resource
    private CartVoMapper cartVoMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TagService tagService;

//    @Resource
//    private OrderCellMapper orderCellMapper;

    @Resource
    private OrderVoService orderVoService;

    @Autowired
    private DictionaryService dictionaryService;

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Resource
    private CartShopVoMapper cartShopVoMapper;

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private FavouriteMapper favouriteMapper;

    @Resource
    private EnterpriseMapper enterpriseMapper;


    Random random = new Random();

    @Resource
    private ShopItemMapper shopItemMapper;

    @Resource
    private TagMapper tagMapper;


    @BeforeAll
    @Test
    void initLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = new User();
        user.setUsername("444");
        session.setAttribute("SW_USER", user);
    }

    class DicVo extends Dictionary {
        public List<DicVo> dicVoList;
    }

    @Test
    DicVo insertDic() {
        DicVo dictionary = new DicVo();
        dictionary.setName("种类");
        dictionary.setId(UUID.randomUUID().toString());
        dictionary.setCreateDate(new Date());
        dictionary.setValue("TYPE_DIC");
        dictionary.setOrder(1);
        dictionaryMapper.addDictionary(dictionary);

        List<DicVo> dictionaries1 = new ArrayList<>();
        List<DicVo> dictionaries2;
        List<DicVo> dictionaries3;
        List<DicVo> dictionaries4;

        dictionary.dicVoList = dictionaries1;
        for (int i = 0; i < 10; i++) {
            DicVo dictionary1 = new DicVo();
            dictionary1.setName("种类" + i);
            dictionary1.setId(UUID.randomUUID().toString());
            dictionary1.setParentId(dictionary.getId());
            dictionary1.setCreateDate(new Date());
            dictionary1.setValue(i + "");
            dictionary1.setOrder(i + 1);
            dictionaries1.add(dictionary1);
            dictionaries2 = new ArrayList<>();

            dictionary1.dicVoList = dictionaries2;
            for (int j = 0; j < 10; j++) {
                DicVo dictionary2 = new DicVo();
                dictionary2.setName("类型" + j);
                dictionary2.setId(UUID.randomUUID().toString());
                dictionary2.setCreateDate(new Date());
                dictionary2.setValue(j + "");
                dictionary2.setOrder(j + 1);
                dictionary2.setParentId(dictionary1.getId());
                dictionaries2.add(dictionary2);
                dictionaries3 = new ArrayList<>();

                dictionary2.dicVoList = dictionaries3;
                for (int k = 0; k < 5; k++) {
                    DicVo dictionary3 = new DicVo();
                    dictionary3.setName("参数" + k);
                    dictionary3.setId(UUID.randomUUID().toString());
                    dictionary3.setCreateDate(new Date());
                    dictionary3.setValue(k + "");
                    dictionary3.setOrder(k + 1);
                    dictionary3.setParentId(dictionary2.getId());
                    dictionaries3.add(dictionary3);
                    dictionaries4 = new ArrayList<>();

                    dictionary3.dicVoList = dictionaries4;
                    for (int l = 0; l < 7; l++) {
                        DicVo dictionary4 = new DicVo();
                        dictionary4.setName("标签" + k + l);
                        dictionary4.setId(UUID.randomUUID().toString());
                        dictionary4.setCreateDate(new Date());
                        dictionary4.setValue(l + "");
                        dictionary4.setOrder(l + 1);
                        dictionary4.setParentId(dictionary3.getId());
                        dictionaries4.add(dictionary4);
                    }
                    dictionaryMapper.batchAddDictionary(dictionaries4);
                }
                dictionaryMapper.batchAddDictionary(dictionaries3);
            }
            dictionaryMapper.batchAddDictionary(dictionaries2);
        }
        dictionaryMapper.batchAddDictionary(dictionaries1);
        return dictionary;
    }

    @Test
    void insertEnterpriseAndShop(DicVo dicVo) {
        String password = new BCryptPasswordEncoder().encode("123");
        List<ShopItem> shopItemList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        List<Shop> shopList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Enterprise> enterpriseList = new ArrayList<>();
        //种类
        for (DicVo type : dicVo.dicVoList) {

            //类型（每个类型700个店铺）
            for (DicVo style : type.dicVoList) {

                //创建商家（每个商家7个店铺，共100个商家）
                for (int i = 0; i < 100; i++) {

                    //创建user,再注册为商家
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setUsername(type.getValue() + style.getValue() + i + "");
                    user.setEnable('1');
                    user.setPassword(password);
                    user.setRole(AuthorityConstant.shop);
                    user.setEnterpriseId(UUID.randomUUID().toString());
                    userList.add(user);

                    Enterprise enterprise = new Enterprise();
                    enterprise.setId(user.getEnterpriseId());
                    enterprise.setUserId(user.getId());
                    enterprise.setEnterpriseName(type.getValue() + style.getValue() + i + "s");
                    enterprise.setPhoneNumber("123");
                    enterpriseList.add(enterprise);

                    //创建店铺
                    for (int j = 0; j < 7; j++) {
                        Shop shop = new ShopVo();
                        shop.setId(UUID.randomUUID().toString());
                        shop.setEnterpriseId(enterprise.getId());
                        shop.setEnable('1');
                        shop.setDescribe("123");
                        shop.setPrice("0~900");
                        shop.setType(type.getId());
                        shop.setStyle(style.getId());
                        shop.setSales(random.nextInt(100));
                        shop.setName(enterprise.getEnterpriseName() + j);
                        shopList.add(shop);

                        //添加标签
                        tagList.addAll(getRandomTagListByStyleVo(style, shop.getId()));

                        //每个店铺最多5个商品，最少一个
                        for (int k = 0; k < random.nextInt(4) + 1; k++) {
                            ShopItem shopItem = new ShopItem();
                            shopItem.setShopId(shop.getId());
                            shopItem.setId(UUID.randomUUID().toString());
                            shopItem.setName(shop.getName() + k);
                            shopItem.setPrice((k + 1) * 100.00);
                            shopItem.setStock(10);
                            shopItemList.add(shopItem);
                        }
                    }
                }
                userMapper.batchInsertUser(userList);
                userList.clear();
                enterpriseMapper.batchAddEnterprise(enterpriseList);
                enterpriseList.clear();
                shopMapper.batchCreateNewShop(shopList);
                shopList.clear();

                tagMapper.addTapList(tagList);
                tagList.clear();
//                tagMapper.addTapList(tagList.subList(0,6000));
//                tagMapper.addTapList(tagList.subList(6000,tagList.size()));
//
                shopItemMapper.batchAddNewShopItem(shopItemList);
                shopItemList.clear();
//                shopItemMapper.batchAddNewShopItem(shopItemList.subList(0,6000));
//                shopItemMapper.batchAddNewShopItem(shopItemList.subList(6000,shopItemList.size()));
            }
        }
    }

    private List<? extends Tag> getRandomTagListByStyleVo(DicVo style, String shopId) {
        List<Tag> tagList = new ArrayList<>();
        for (DicVo param : style.dicVoList) {
            if (random.nextInt(3) == 0){
                continue;
            }
            int beginIndex = random.nextInt(6);
            int endIndex = beginIndex + random.nextInt(7 - beginIndex);
            List<DicVo> tagSubList = param.dicVoList.subList(beginIndex, endIndex);
            for (DicVo tag : tagSubList) {
                tagList.add(dicVoTransToTag(tag, shopId));
            }
        }
        return tagList;
    }

    private Tag dicVoTransToTag(DicVo dicVo, String shopId) {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID().toString());
        tag.setDicId(dicVo.getId());
        tag.setName(dicVo.getName());
        tag.setShopId(shopId);
        return tag;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Commit
    void insertTestData() {
        //插入字典
        DicVo dicVo = insertDic();
        //插入商家和店铺
        insertEnterpriseAndShop(dicVo);
    }

    @Test
    void testFavouriteMapper() throws Exception {

        List<Favourite> favouriteList = new ArrayList<>();
        favouriteList.add(new Favourite("7ba510b1-9bc9-4748-9fe1-cff83eafeb28", "6d7ca88c-8cdc-4284-a193-28a53ca45c83"));
        favouriteList.add(new Favourite("7ba510b1-9bc9-4748-9fe1-cff83eafeb28", "d10baf82-686e-4607-965d-ced5414bc0e9"));
//        favouriteMapper.insertOrUpdateFavouriteList(favouriteList);
    }

    @Test
    void testTansaction() throws Exception {
        List<Tag> tags = new ArrayList<>();
        Tag tag;
        for (int i = 0; i < 10; i++) {
            tag = new Tag();
            tag.setId(UUID.randomUUID().toString());
            tag.setName(i + "");
            tags.add(tag);
        }
        tagService.addTapList(tags, "1c6ada27-0fc6-4bc3-b69c-e7178028f76a");
//        tagService.updateTapList(tags,"1c6ada27-0fc6-4bc3-b69c-e7178028f76a");
    }

    @Test
    void testShopMapper() throws Exception {
//        List<Shop> shopList = shopMapper.getCollectShopListByPage("7ba510b1-9bc9-4748-9fe1-cff83eafeb285");
//        System.out.println(shopList);
//        List<Shop> shopList = shopMapper.getShopListByStyleIds(QueryPramFormatUtils.strToIn("2145b92a-53d3-4618-a8db-073929cc9e5a,00500d57-6f09-4c00-b467-8eaa4878ceac,3bda322e-f875-40a2-81c9-b704a1276114",","));
//        System.out.println(shopList);
        shopMapper.getFavouriteShopList("7ba510b1-9bc9-4748-9fe1-cff83eafeb285");
    }

    @Test
    void testCartShopVoMapper() throws Exception {
        List<CartShopVo> shopVoList = cartShopVoMapper.getCartShopVoListByUserId("7ba510b1-9bc9-4748-9fe1-cff83eafeb285");
        System.out.println(shopVoList);
        List<String> ids = new ArrayList<>();
        for (CartShopVo cartShopVo : shopVoList) {
            for (CartVo cartVo : cartShopVo.getCartVoList()) {
                ids.add(cartVo.getId());
            }
        }
        String str = QueryPramFormatUtils.listToIn(ids);
        System.out.println(str);
        List<CartShopVo> cartShopVoListByIds = cartShopVoMapper.getCartShopVoListByIds(str);
        System.out.println(cartShopVoListByIds);
    }

    @Test
    void testDictionaryMapper() {
//        Dictionary dictionary = new Dictionary();
//        dictionary.setName("种类1" );
//        dictionary.setId(UUID.randomUUID().toString());
//        dictionary.setCreateDate(new Date());
//        dictionary.setValue(1 + "");
//        dictionary.setOrder( 1);
//        dictionaryMapper.addDictionary(dictionary);
//        dictionary.setName("种类2" );
//        dictionary.setId(UUID.randomUUID().toString());
//        dictionary.setCreateDate(new Date());
//        dictionary.setValue(2 + "");
//        dictionary.setOrder( 2);
//        dictionaryMapper.addDictionary(dictionary);
//        List<Dictionary> rootDictionaryList = dictionaryMapper.getRootDictionaryList();
//        System.out.println(rootDictionaryList);
//
//        Dictionary dictionary = new Dictionary();
//        dictionary.setName("种类" );
//        dictionary.setId(UUID.randomUUID().toString());
//        dictionary.setCreateDate(new Date());
//        dictionary.setValue("TYPE_DIC");
//        dictionary.setOrder( 1);
//        dictionaryMapper.addDictionary(dictionary);
//        List<Dictionary> dictionaries1 = new ArrayList<>();
//        List<Dictionary> dictionaries2;
//        List<Dictionary> dictionaries3;
//        List<Dictionary> dictionaries4;
//        for (int i = 0; i < 10; i++) {
//            Dictionary dictionary1 = new Dictionary();
//            dictionary1.setName("种类" + i);
//            dictionary1.setId(UUID.randomUUID().toString());
//            dictionary1.setParentId(dictionary.getId());
//            dictionary1.setCreateDate(new Date());
//            dictionary1.setValue(i + "");
//            dictionary1.setOrder(i + 1);
//            dictionaries1.add(dictionary1);
//            dictionaries2 = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                Dictionary dictionary2 = new Dictionary();
//                dictionary2.setName("类型" + j);
//                dictionary2.setId(UUID.randomUUID().toString());
//                dictionary2.setCreateDate(new Date());
//                dictionary2.setValue(j + "");
//                dictionary2.setOrder(j + 1);
//                dictionary2.setParentId(dictionary1.getId());
//                dictionaries2.add(dictionary2);
//                dictionaries3 = new ArrayList<>();
//                for (int k = 0; k < 10; k++) {
//                    Dictionary dictionary3 = new Dictionary();
//                    dictionary3.setName("参数" + k);
//                    dictionary3.setId(UUID.randomUUID().toString());
//                    dictionary3.setCreateDate(new Date());
//                    dictionary3.setValue(k + "");
//                    dictionary3.setOrder(k + 1);
//                    dictionary3.setParentId(dictionary2.getId());
//                    dictionaries3.add(dictionary3);
//
//                    dictionaries4 = new ArrayList<>();
//                    for (int l = 0; l < 10; l++) {
//                        Dictionary dictionary4 = new Dictionary();
//                        dictionary4.setName("标签" + k + l);
//                        dictionary4.setId(UUID.randomUUID().toString());
//                        dictionary4.setCreateDate(new Date());
//                        dictionary4.setValue(l + "");
//                        dictionary4.setOrder(l + 1);
//                        dictionary4.setParentId(dictionary3.getId());
//                        dictionaries4.add(dictionary4);
//                    }
//                    dictionaryMapper.batchAddDictionary(dictionaries4);
//                }
//                dictionaryMapper.batchAddDictionary(dictionaries3);
//            }
//            dictionaryMapper.batchAddDictionary(dictionaries2);
//        }
//        dictionaryMapper.batchAddDictionary(dictionaries1);

//        DictionaryVo dictionaryVoByParentId = dictionaryMapper.getDictionaryVoByParentId("a044c3db-483d-4c02-8010-e39ff4090726");
//        System.out.println(dictionaryVoByParentId);
//        DictionaryVo dictionaryVoByParentId = dictionaryMapper.getRootDictionaryVoByValue("1");
//        System.out.println(dictionaryVoByParentId);
        List<Dictionary> tagDictionaryListByStyleId = dictionaryMapper.getTagDictionaryListByStyleIdAndKeyWord("00500d57-6f09-4c00-b467-8eaa4878ceac", "2");
        System.out.println(tagDictionaryListByStyleId);
    }

    @Test
    void testkeyword() throws Exception {
        String str = "撒旦法阿达";
        char[] chars = str.toCharArray();
        System.out.println(chars);
    }

    @Test
    void testOrderVoService() throws Exception {
        List<OrderVo> orderVo = orderVoService.getUnFinishOrderVoByUserId();
        System.out.println(orderVo);
    }

    @Test
    void testCreateOrder() {
        String[] ids = {"41"};
        try {
            orderService.createOrder(ids, "1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testMultiInsert() {
        List<CartVo> cartVos = cartVoMapper.getCartWithGoodsInfoByIds("(35,38)");
        List<OrderCell> orderCells = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            orderCells.add(cartVo.convertToOrderCell());
        }
//        orderCellMapper.insertOrderCells(orderCells);
    }

    @Test
    void r() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tbl_user");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));
            System.out.println(resultSet.getInt(3));
            System.out.println(resultSet.getString("email"));
        }
        resultSet.close();
        connection.close();
    }

    @Test
    void r1() {
        System.out.println(userMapper.getAll());
    }

    @Test
    void rename() {
        File picdir = new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic");
        File[] files = picdir.listFiles();
        int i = 1;
        for (File pic :
                files) {
//            pic.renameTo(i+"");
            pic.renameTo(new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic\\" + i + ".jpg"));
            i++;
        }

    }

    @Test
    void password() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println(encode);
    }

    @Test
    void updatelastdate() {
        userService.updateLastLoginDateByUsername("444");
    }

    @Test
    void testCartGoodsMapper() {
        List<CartVo> carts = cartVoMapper.getCartWithGoodsInfoByUserId("7ba510b1-9bc9-4748-9fe1-cff83eafeb28");
        System.out.println(carts);
    }


}
