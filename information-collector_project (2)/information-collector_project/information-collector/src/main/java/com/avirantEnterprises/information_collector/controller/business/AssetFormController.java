package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.model.business.Asset;
import com.avirantEnterprises.information_collector.service.business.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AssetFormController {

    @Autowired
    private AssetService assetService;


    @GetMapping("/asset/{id}")
    public String viewAsset(@PathVariable Long id, Model model) {
        Asset asset = assetService.getAssetById(id);
        model.addAttribute("asset", asset);
        return "business/asset_view";
    }


    @GetMapping("/asset/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        assetService.deleteAssetById(id);
        return "redirect:/asset_list";
    }


    @GetMapping("/asset_list")
    public String listAsset(Model model) {
        List<Asset> assets = assetService.getAllAssets();
        model.addAttribute("assets", assets);
        return "business/asset_list";
    }


    @GetMapping("/asset/update/{id}")
    public String showUpdateAssetForm(@PathVariable Long id, Model model) {
        Asset asset = assetService.getAssetById(id);
        model.addAttribute("asset", asset);
        return "business/asset_update";
    }


    @PostMapping("/updateAsset")
    public String updateAsset(@RequestParam("id") Long id,
                                 @RequestParam("assetName") String assetName,
                                 @RequestParam("assetId") String assetId,
                                 @RequestParam("category") String category,
                                 @RequestParam("description") String description,
                                 @RequestParam("document") MultipartFile document) {
        assetService.updateAsset(id, assetName, assetId, category, description, document);
        return "redirect:/asset/" + id;
    }


    @GetMapping("/asset/new")
    public String showAssetForm(Model model) {
        return "business/asset_form";
    }


    @PostMapping("/submitAsset")
    public String submitAsset(@RequestParam("assetName") String assetName,
                              @RequestParam("assetId") String assetId,
                              @RequestParam("category") String category,
                              @RequestParam("description") String description,
                              @RequestParam("document") MultipartFile document) {
        assetService.saveAsset(assetName, assetId, category, description, document);
        return "business/success";
    }
}
