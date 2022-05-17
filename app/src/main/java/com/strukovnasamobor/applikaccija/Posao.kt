package com.strukovnasamobor.applikaccija

class Posao(naziv: String,
            opis: String,
            tag1:String,
            tag2: String,
            tag3:String) {

    private lateinit var naziv:String
    private lateinit var opis:String
    private lateinit var tag1:String
    private lateinit var tag2:String
    private lateinit var tag3:String

    //SETTERI
    public fun set_naziv(naziv:String){
        this.naziv=naziv
    }
    public fun set_opis(opis:String){
        this.opis=opis
    }
    public fun set_tag1(tag1:String){
        this.tag1=tag1
    }
    public fun set_tag2(tag2:String){
        this.tag2=tag2
    }
    public fun set_tag3(tag3:String){
        this.tag3=tag3
    }

    //GETTERI
    public fun get_naziv():String{
        return naziv
    }
    public fun get_opis():String{
        return opis
    }
    public fun get_tag1():String{
        return tag1
    }
    public fun get_tag2():String{
        return tag2
    }
    public fun get_tag3():String{
        return tag3
    }


}