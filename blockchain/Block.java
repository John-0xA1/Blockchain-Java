package blockchain;

import java.time.LocalTime;
import java.util.Date;

public class Block {
    private volatile static int uid_pool = 0;
    private int uid;
    private int minerId;
    private String hash;
    private String previousHash;
    private long timeStamp;
    private long timeToGenerate;
    private long magicNumber;
    private String conditionChange;

    public Block(Blockchain blockchain, int minerId) {
        uid = blockchain.getLastInserted() == null ? 1 : blockchain.getLastInserted().getUid() + 1;
        this.minerId = minerId;
        previousHash = blockchain.getLastInserted() == null ? "0" : blockchain.getLastInserted().getHash();
        timeStamp = new Date().getTime();
        long before_creation = LocalTime.now().toSecondOfDay();
        this.magicNumber = generateMagicNumber(blockchain.getLeading_zeroes());

    }

    private long generateMagicNumber(int leading_zeroes) {

        String toCompare = "";
        for (int i = 0; i < leading_zeroes; i++) {
            toCompare += "0";
        }

        long magicNumber = 0;
        long before_creation = LocalTime.now().toSecondOfDay();
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            String currentHash = StringUtil.applySha256(this + String.valueOf(i));
            if (currentHash.substring(0, leading_zeroes).equals(toCompare)) {
                setHasH(currentHash);
                magicNumber = i;
                break;
            }
        }
        long after_creation = LocalTime.now().toSecondOfDay();
        setTimeToGenerate(after_creation - before_creation);
        return magicNumber;
    }

    public boolean validate(Block block) {
        if (block == null) {
            return true;
        }
        else {
            if (block.getUid() + 1 == this.getUid()) {
                return true;
            }
            else {
                return false;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder blockStringBuilder = new StringBuilder();
        blockStringBuilder.append("Block:" + "\n"
                + "Created by miner" + this.getMinerId() + "\n"
                + "miner" + this.getMinerId() + " gets 100 VC" + "\n"
                + "Id: " + this.getUid() + "\n"
                + "Timestamp: " + this.getTimeStamp() + "\n"
                + "Magic number: " + this.getMagicNumber() + "\n"
                + "Hash of the previous block: \n" + this.getPreviousHash() + "\n"
                + "Hash of the block: \n" + this.getHash() + "\n"
                + "Block data: " + "\n"
                + "miner1 send 30 VC to miner 2" + "\n"
                + "Block was generating for " + this.getTimeToGenerate() + " seconds" + "\n"
                + this.getConditionChange() + "\n"
        );

        return blockStringBuilder.toString();
    }

    void setTimeToGenerate(long timeToGenerate) {
        this.timeToGenerate = timeToGenerate;
    }

    void setHasH(String hash) {
        this.hash = hash;
    }

    void setConditionChange (String state) {
        this.conditionChange = state;
    }



    public int getUid() {
        return uid;
    }

    public int getMinerId() {
        return minerId;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getTimeToGenerate() {
        return timeToGenerate;
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    public String getConditionChange() {
        return conditionChange;
    }
}
