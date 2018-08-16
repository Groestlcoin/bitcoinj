/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.params;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.*;

import java.net.*;

import static com.google.common.base.Preconditions.*;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;

    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 36;
        p2shHeader = 5;
        p2wpkhHeader = 6;
        p2wshHeader = 10;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader, p2wpkhHeader, p2wshHeader };
        port = 1331;
        packetMagic = 0xf9beb4d4;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"
        bip49HeaderPub = 0x049D7CB2; //The 4 byte header that serializes in base58 to "ypub".
        bip49HeaderPriv = 0x049D7878; //The 4 byte header that serializes in base58 to "yprv"
        bip84HeaderPub = 0x04B24746; //The 4 byte header that serializes in base58 to "zpub".
        bip84HeaderPriv = 0x04B2430C; //The 4 byte header that serializes in base58 to "zprv"

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        genesisBlock.setDifficultyTarget(0x1e0fffffL);
        genesisBlock.setTime(1395342829L);
        genesisBlock.setNonce(220035);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("00000ac5927c594d49cc0bdb81759d0da8297eb614683d3acb62f0703b639023"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put( 28888, Sha256Hash.wrap("00000000000228ce19f55cf0c45e04c7aa5a6a873ed23902b3654c3c49884502"));
        checkpoints.put( 58888, Sha256Hash.wrap("0000000000dd85f4d5471febeb174a3f3f1598ab0af6616e9f266b56272274ef"));
        checkpoints.put(111111, Sha256Hash.wrap("00000000013de206275ee83f93bee57622335e422acbf126a37020484c6e113c"));
        checkpoints.put(222222, Sha256Hash.wrap("00000000077f5f09288285b68d68636e652f672279ba6a014fdc404fe902a631"));
        checkpoints.put(333333, Sha256Hash.wrap("00000000121e477df65543cd7289c4790ad6fee6cfa48b4391cc594141356d5f"));
        checkpoints.put(444444, Sha256Hash.wrap("0000000029558b975a9507a32895735b9d9437308fa3302e02ca3103d61d6447"));
        checkpoints.put(555555, Sha256Hash.wrap("000000000d62c3e8b8983f21b86123a5deea9ebabd162c6b13428e92866a6c1f"));
        checkpoints.put(666666, Sha256Hash.wrap("000000000603db2b89e2194214d41ccff6d1181e2d80774cf65d834e5bbd8ea1"));
        checkpoints.put(777777, Sha256Hash.wrap("00000000000649a9cd35bc39ab705379139cd72142dcba83c79ca425bc399c0b"));
        checkpoints.put(888888, Sha256Hash.wrap("000000000cce6537f3d8b22ab70a9925ba34bd9a9f850c9644f69d11ba0393e9"));
        checkpoints.put(917000, Sha256Hash.wrap("0000000012fe97c83a908d3217ce2df025fb8ba32c88bd86a14eb329e6c7259d"));

        dnsSeeds = new String[] {
                "groestlsight.groestlcoin.org",
                "groestlcoin.org",
                "electrum1.groestlcoin.org",
                "electrum2.groestlcoin.org",
                "electrum20.groestlcoin.org",
                "jswallet.groestlcoin.org",
        };
        httpSeeds = new HttpDiscovery.Details[] {
                // Groestlcoin
                new HttpDiscovery.Details(
                        ECKey.fromPublicOnly(Utils.HEX.decode("0248876142c407e9a05a07f96caf212eb5b54b68845ddee44739094b02e24d13e4")),
                        URI.create("http://groestlcoin.org:8080/peers")
                )
        };

        addrSeeds = null;
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
