package com.libi.model;

import com.libi.bean.NftPassRank;
import jnr.ffi.annotations.In;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PassRankRsp {
    private List<Rank> rankList;

    public static PassRankRsp of(List<NftPassRank> allRank) {
        List<Rank> list = new ArrayList<>();
        for (NftPassRank nftPassRank : allRank) {
            list.add(Rank.of(nftPassRank));
        }
        PassRankRsp passRankRsp = new PassRankRsp();
        passRankRsp.setRankList(list);
        return passRankRsp;
    }

    @Data
    public static class Rank{
        private String rankId;
        private String desc;
        private Boolean permanent;
        private Integer passDays;
        private String price;
        private String unit;

        public static Rank of(NftPassRank nftPassRank) {
            Rank rank = new Rank();
            rank.rankId = nftPassRank.getId().toString();
            rank.desc = nftPassRank.getDescp();
            rank.permanent = nftPassRank.getDays() < 0;
            rank.passDays = nftPassRank.getDays();
            rank.price = nftPassRank.getEthNum().toString();
            rank.unit = nftPassRank.getEthUnit();
            return rank;
        }
    }
}
