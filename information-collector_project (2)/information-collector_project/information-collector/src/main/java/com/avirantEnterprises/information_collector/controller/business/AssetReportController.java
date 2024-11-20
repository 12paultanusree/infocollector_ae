package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AssetReportController {

    @Autowired
    private AssetService assetReportService;

    @GetMapping("/asset_form")
    public String showAssetReportForm() {
        return "business/asset_form";
    }

    @PostMapping("/submitAssetForm")
    public String submitAsset(
            @RequestParam("assetName") String assetName,
            @RequestParam("assetId") String assetId,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("document") MultipartFile document) {

        assetReportService.saveAsset(assetName, assetId, category, description, document);
        return "business/success";
    }
}
