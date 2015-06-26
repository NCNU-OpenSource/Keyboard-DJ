# Keyboard-DJ  
題目發想緣起：  
　　自對音控的熱愛  
　　便利的音控系統  
　　輕便的硬體設備  
實作所需材料（取得來源、價位）：  
　　Raspberry Pi (課程提供)  
　　Keyboard ($199購得)  
使用的現有軟體與來源：  
    Java Library - JLine(v1.0): http://jline.sourceforge.net/   
    Raspbian 內建Shell指令播放軟體  
     - Aplay(播放 *.wav)  
     - OMXPlayer(播放 *.mp3)  
  
實作過程（碰到哪些問題、如何解決）： 
　　＊JavaFX在Raspbian的支援度不足  
　　利用JavaFX開發的GUI應用程式在PI上運行時容易產生錯誤，或是運行時會導致系統死當。  
　　且JavaFX程式必須要在GUI作業系統上運行，效能容易被GUI拖慢  
　　＞解決方法：改調用Shell來運行comman  
　　
　　＊OMXPlayer播放延遲  
　　OMXPlayer播放時約有1秒鐘的延遲  
　　＊Aplay格式不支援  
　　Aplay再撥放wav時會產生雜訊  
　　＞解決方法：音效(*.wav)以Aplay播放；音樂(*.mp3 or *.wav)以OMXPlayer播放  
　　
運用哪些與課程內容中相關的技巧：  
　　top, kill, ps 等針對Process監看/管理指令  
組裝過程及製作教學（GPIO線材的安裝、3D列印後的組裝...）：  
　　1.鍵盤、音源、螢幕直接由PI的外部介面進行連接  
　　2.設定 Raspbian Config(音源以3.5mm輸出)  
　　>[raspi-config]內進行設定(參考：https://www.raspberrypi.org/documentation/configuration/audio-config.md )  
　　3.設置HDMI轉VGA轉接器  
　　>[/boot/config.txt] (參考：https://sites.google.com/site/raspberypishare0918/home/di-yi-ci-qi-dong/1-7-hdmi-zhuan-vga  
　　4.將本程式原始碼與JLine-1.0.jar進行編譯  
　　>javac -cp .:jline-1.0.jar ./tw/edu/ncnu/lsa/ShellDJ.java  
　　5.音樂檔命名後放置本目錄(.)  
　　#音效檔格式為(*.wav) -> sound[97~122].wav  
　　#音樂檔格式為(*.mp3 or *.wav) -> music[65~90].mp3 or music[65~90].wav  
　　>數字對應Ascii碼 65~90(A~Z) 97~122(a~z)  
　　6.執行本程式  
　　>java -cp .:jline-1.0.jar ./tw/edu/ncnu/lsa/ShellDJ  
　　
操作教學（做出此產品之後該如何操作）：  
    出現Ready後即可進行操作  
　　#?鍵：顯示可以輸入的聲音/音效按鍵  
　　#A~Z：播放/停止對應音樂，第一次:播放 第二次:停止  
　　#a~z：播放對應音樂/音效  
　　  
工作分配表：  
　　題目發想：哲瑋、騰右、曉柔  
　　程式撰寫：哲瑋  
　　系統展示：騰右  
　　投影片製作：曉柔、哲瑋  
參考來源：  
　　PLAYING AUDIO ON THE RASPBERRY PI  
　　https://www.raspberrypi.org/documentation/usage/audio/  
　　
