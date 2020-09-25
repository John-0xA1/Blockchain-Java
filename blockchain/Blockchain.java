package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Blockchain implements Serializable {
    private static int size = 0;
    private int block_limit;
    private static int leading_zeroes = 0;
    private boolean miningProcess;
    private volatile Block lastInserted = null;
    private ArrayList<Block> chain;
    private transient ExecutorService clientsPool;



    public Blockchain(int block_limit) {
        this.block_limit = block_limit;
        this.chain = new ArrayList<>();
    }

    private void resize() {
        this.size *= 2;
    }

    public synchronized void addBlock(Block block) {

        if (lastInserted != null) {
            if (block.getUid() != lastInserted.getUid() + 1) {
                return;
            }
        }

        if (block.getTimeToGenerate() < 2) {
            block.setConditionChange("N was increased to " + leading_zeroes);
        }
        else if (block.getTimeToGenerate() > 5) {
            block.setConditionChange("N was decreased to " + --leading_zeroes);
        }
        else {
            block.setConditionChange("N stays the same");
        }
        if (lastInserted == null || lastInserted.getHash() == block.getPreviousHash()) {
            chain.add(block);
            size++;
            lastInserted = block;
            System.out.println(block);
        }

        if (size >= block_limit) {
            stopMining();
        }
    }

    public void startMining(int minersNumber) {
        clientsPool = Executors.newFixedThreadPool(minersNumber);
        miningProcess = true;
        for (int i = 0; i < minersNumber; i++) {
            clientsPool.submit(new Miner(this, i + 1));
        }
    }

    public void stopMining() {
        miningProcess = false;
        if (clientsPool != null) {
            clientsPool.shutdownNow();
            try {
                clientsPool.awaitTermination(1, TimeUnit.SECONDS);

            }
            catch (Exception ex) {
                System.out.println(ex.getStackTrace());
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : chain) {
            stringBuilder.append(block.toString());
        }
        return stringBuilder.toString();
    }


    public int getSize() {
        return size;
    }
    public boolean isMiningProcess() {
        return miningProcess;
    }

    public int getLeading_zeroes() {
        return leading_zeroes;
    }

    public Block getLastInserted() {
        return lastInserted;
    }

    public ArrayList<Block> getChain() {
        return chain;
    }
}
