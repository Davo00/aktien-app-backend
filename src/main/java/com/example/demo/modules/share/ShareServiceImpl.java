package com.example.demo.modules.share;

import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<Share> findAllShare() {
        List<Share> allShare = shareRepository.findAll();
        return allShare;
    }

    @Override
    public Share createShare(Share request) {
        Share share = new Share(request.getId(), request.getName());
        share = shareRepository.save(share);
        return share;
    }


    @Override
    public Share one(long id) {
        Share share = shareRepository.getOne(id);

        return share;
    }

    @Override
    public Share deleteShare(long id) throws Exception {
        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Group could not be found"));
        try{shareRepository.delete(share);
        }catch (Exception e ){
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Share> getPreferedSharesbyUser(long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User could not be found!"));
     //   List<Share> shares = Wird nachgeholt, wenn User_sharelist da ist

        return null;
    }



}
