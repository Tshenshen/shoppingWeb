package com.liang.shoppingweb.service.shop;

import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.mapper.shop.ShopMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ConfigurationProperties(prefix = "shop")
@Service
public class ShopService {

    private String imagePath = "src/main/resources/static/image/";

    @Resource
    private ShopMapper shopMapper;

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

    public void createNewShop(Shop shop) {
        shop.setId(UUID.randomUUID().toString());
        shop.setEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
        shop.setCreateDate(new Date());
        shop.setEnable('1');
        shopMapper.createNewShop(shop);
    }

    public void updateShopEnable(Shop shop) {
        shop.setUpdateDate(new Date());
        shopMapper.updateShopEnable(shop);
    }

    public void deleteShopById(String id) throws Exception{
        shopMapper.deleteShopById(id);
        File shopDir = new File(imagePath + id);
        if (shopDir.exists()){
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

    public void updateShopInfoById(Shop shop) {
        shop.setUpdateDate(new Date());
        shopMapper.updateShopInfoById(shop);
    }
}
