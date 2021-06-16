package com.example.demo.modules.share;

 public class ShareNotFoundException extends RuntimeException {

   ShareNotFoundException(long id) {
        super("Aktie " + id  + " konnte nicht gefunden werden.");
    }
}