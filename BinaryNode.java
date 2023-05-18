class BinaryNode {
    String key;
    BinaryNode left;
    BinaryNode right;
    Drug drug;
    public BinaryNode(Drug drug){
        this.key= drug.drugBankID;
        this.left=null;
        this.right=null;
         this.drug=drug;
    }



}
