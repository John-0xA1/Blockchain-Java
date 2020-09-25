package blockchain;

public class Main {

    public static void main(String [] args) {
        Blockchain blockchain = new Blockchain(15);

        //blockchain.startMining(4);
        blockchain.addBlock(new Block(blockchain, 1));
        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));
        blockchain.addBlock(new Block(blockchain, 1));
        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));
        blockchain.addBlock(new Block(blockchain, 1));
        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

        blockchain.addBlock(new Block(blockchain, 1));

    }
}
