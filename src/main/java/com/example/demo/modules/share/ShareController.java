package com.example.demo.modules.share;

import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.modules.user.UserService;
import com.example.demo.utils.DeletionIntegrityException;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/share")
public class ShareController {
    final private String API_KEY = "C5R0KD15LKFP929I";

    private final ShareService shareService;
    private final UserService userService;
    private final UserService debtService;

    @Autowired
    public ShareController(ShareService shareService, UserService userService, UserService debtService) {
        this.shareService = shareService;
        this.userService = userService;
        this.debtService = debtService;
    }


    @GetMapping
    public ResponseEntity<List<Share>> findAllShare() throws Exception {
        return ResponseEntity.ok(shareService.findAllShare());
    }

    @PostMapping
    public ResponseEntity<Share> createShare(@RequestHeader("Authorization") String token,
                                             @RequestBody @Valid CreateShare request, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        Share share = shareService.createShare(request);
        UriComponents uriComponents = uriComponentsBuilder.path("share/{name}").buildAndExpand(share.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(share);
    }

    @GetMapping("{id}")
    public ResponseEntity<Share> one(@RequestHeader("Authorization") String token,
                                     @PathVariable long id) throws Exception {
        Optional<Share> share = Optional.ofNullable(shareService.one(id));
        Share s = share.orElseThrow(() -> new NotFoundException("Share could not be found"));
        return ResponseEntity.ok(s);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Share> deleteShare(@RequestHeader("Authorization") String token,
                                             @PathVariable long id) throws NotFoundException, DeletionIntegrityException {
        shareService.deleteShare(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{username}")
    public ResponseEntity<List<Share>> getPreferedSharesbyUsername(@RequestHeader("Authorization") String token,
                                                                   @PathVariable String username) throws Exception {
        Optional<List<Share>> share = Optional.ofNullable(shareService.getPreferedSharesbyUser(username));
        List<Share> s = share.orElseThrow(() -> new NotFoundException("Sharelist could not be found"));
        return ResponseEntity.ok(s);
    }

    @GetMapping("debtValue/{debtId}")
    public double getCurrentDebtValue(@RequestHeader("Authorization") String token,
                                      @PathVariable Long debtId) throws Exception {
        return shareService.getSharePriceByDebt(debtId);
    }

    @PostMapping("share_id/{username}")
    public ResponseEntity<Share> selectShareFromPartner(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long share_id,
                                                        @PathVariable String username) throws NotFoundException {
        Share s = null;
        //To do
        return ResponseEntity.ok(s);
    }


    public Stock getShareByName(String shareName) throws IOException {
        URL url = buildUrl(shareName);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        String output = br.readLine();
        return parseStringToJson(output);
    }

    private URL buildUrl(String shareName) throws MalformedURLException {
        String shareAdress = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=";
        shareAdress += shareName + "&apikey=" + this.API_KEY;
        return new URL(shareAdress);
    }

    public Stock parseStringToJson(String stock) {
        Gson gson = new Gson();
        return gson.fromJson(stock, Stock.class);
    }


    @GetMapping("{sharename}")
    public ResponseEntity<?> getStocks(@RequestHeader("Authorization") String token,
                                       @PathVariable String shareName) throws IOException {
        return ResponseEntity.ok(getShareByName(shareName));
    }

}
