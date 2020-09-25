package blockchain;



public class Miner implements Runnable{
    private Blockchain blockchain;
    private int miner_id;

    public Miner(Blockchain blockchain, int miner_id) {
        this.blockchain = blockchain;
        this.miner_id = miner_id;
    }

    @Override
    public void run() {
        while(blockchain.isMiningProcess()) {
            Block block = new Block(blockchain, miner_id);
            if (block.validate(blockchain.getLastInserted())){
                blockchain.addBlock(block);
            }
        }
    }
}
