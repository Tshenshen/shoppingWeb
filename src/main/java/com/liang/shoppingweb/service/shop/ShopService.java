package com.liang.shoppingweb.service.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liang.shoppingweb.common.PageConstant;
import com.liang.shoppingweb.common.UserConstant;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.mapper.shop.ShopMapper;
import com.liang.shoppingweb.mapper.shop.ShopVoMapper;
import com.liang.shoppingweb.service.common.TagService;
import com.liang.shoppingweb.utils.LoginUtils;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
import com.liang.shoppingweb.utils.SearchInfo;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@ConfigurationProperties(prefix = "shop")
@Service
public class ShopService {

    private String imagePath = "src/main/resources/static/image/";

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private ShopVoMapper shopVoMapper;

    @Autowired
    private TagService tagService;

    public ShopVo getShopVoById(String shopId) {
        return shopVoMapper.getShopVoById(shopId);
    }

    public void setImagePath(String imagePath) {
        if (!imagePath.endsWith("/")) {
            this.imagePath = imagePath + "/";
        } else {
            this.imagePath = imagePath;
        }
    }

    public List<Shop> getShopListByEnterpriseId() {
        return shopMapper.getShopListByEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
    }

    @Transactional
    public void createNewShop(ShopVo shopVo) {
        shopVo.setId(UUID.randomUUID().toString());
        shopVo.setEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
        shopVo.setCreateDate(new Date());
        shopVo.setEnable('1');
        shopMapper.createNewShop(shopVo);
        tagService.addTapList(shopVo.getTagList(), shopVo.getId());
    }

    public void updateShopEnable(Shop shop) {
        shop.setUpdateDate(new Date());
        shopMapper.updateShopEnable(shop);
    }

    public void deleteShopById(String id) throws Exception {
        shopMapper.deleteShopById(id);
        File shopDir = new File(imagePath + id);
        if (shopDir.exists()) {
            FileUtils.deleteDirectory(shopDir);
        }
    }

    public String uploadShopImage(String shopId, MultipartFile file) throws IOException {
        Shop shop = shopMapper.getShopById(shopId);
        String imageName = transFile(imagePath + shopId, file);
        if (StringUtils.isEmpty(shop.getImages())) {
            shop.setImages(imageName);
        } else {
            shop.setImages(shop.getImages() + "," + imageName);
        }
        shop.setUpdateDate(new Date());
        shopMapper.updateShopImages(shop);
        return shop.getImages();
    }

    private String transFile(String dir, MultipartFile file) throws IOException {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        String fileUrl = dir + "/" + fileName;
        File shopDir = new File(dir);
        if (!shopDir.exists()) {
            shopDir.mkdirs();
        }
        File destFile = new File(fileUrl);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        file.transferTo(destFile);
        return fileName;
    }

    public String deleteShopImage(String shopId, String imageName) {
        StringBuilder images = new StringBuilder();
        Shop shop = shopMapper.getShopById(shopId);
        deleteFile(imagePath + shopId + "/" + imageName);
        System.out.println(shop.getImages());
        System.out.println(imageName);
        int index = shop.getImages().indexOf(imageName);
        if (index > 0) {
            images.append(shop.getImages(), 0, index - 1).append(shop.getImages().substring(index + imageName.length()));
        } else {
            images.append(shop.getImages().substring(index + imageName.length()));
        }
        shop.setImages(images.toString());
        shop.setUpdateDate(new Date());
        shopMapper.updateShopImages(shop);
        return shop.getImages();
    }

    private void deleteFile(String url) {
        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }
    }

    @Transactional
    public void updateShopInfoById(ShopVo shopVo) {
        shopVo.setUpdateDate(new Date());
        shopMapper.updateShopInfoById(shopVo);
        tagService.updateTapList(shopVo.getTagList(), shopVo.getId());
    }

    public PageInfo<Shop> getShopListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(shopMapper.getShopListByPage(), PageConstant.navigatePages);
    }

    public PageInfo<Shop> getCollectShopListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(shopMapper.getCollectShopListByPage(LoginUtils.getCurrentUserId()));
    }

    public PageInfo<Shop> getShopListBySearchInfo(SearchInfo searchInfo) {
        PageHelper.startPage(searchInfo.getPageNum(), PageConstant.pageSize);
        searchInfo.tagListToTagListQueryString();
        return new PageInfo<>(shopMapper.getShopListBySearchInfo(searchInfo));
    }

    public void addSalesFromOrderVoList(List<OrderVo> orderVoList) {
        new Thread(() -> {
            for (OrderVo orderVo : orderVoList) {
                addSales(orderVo.getShopId(), orderVo.getOrderCells().size());
            }
        }).start();
    }

    private void addSales(String shopId, int sales) {
        shopMapper.addSalesByShopId(shopId, sales);
    }

    public List<ShopVo> getShopWithTagListByEnterpriseId() {
        return shopVoMapper.getShopWithTagListByEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
    }

    public PageInfo<Shop> getRecommendShopList(int pageNum, HttpServletRequest request) {
        PageHelper.startPage(pageNum, PageConstant.pageSize);
        Cookie[] cookies = request.getCookies();
        String styleIds = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName() != null && cookie.getName().equals(UserConstant.recommend)) {
                styleIds = QueryPramFormatUtils.arrayToIn(cookie.getValue().split("#"));
            }
        }
        return new PageInfo<>(shopMapper.getShopListByStyleIds(styleIds));
    }
}
