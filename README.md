# Keyboard-DJ #

## 題目發想緣起 ##  

  - 自對音控的熱愛  
  - 便利的音控系統  
  - 輕便的硬體設備  

## 實作所需材料##

  - Raspberry Pi (課程提供)  
  - Keyboard ($199購得)  

## 使用的現有軟體與來源 ##

  - Java Library - JLine(v1.0)：由 Maven [提供](http://jline.sourceforge.net)
  - Raspbian 內建 Shell 指令播放軟體  
    - Aplay：播放 \*.wav
    - OMXPlayer：播放 \*.mp3

## 實作過程（碰到哪些問題、如何解決）##

### JavaFX 在 Raspbian 的支援度不足 ###

利用 JavaFX 開發的 GUI 應用程式在 Raspberry Pi 上運行時容易產生錯誤，或是運行時會導致系統死當。且 JavaFX 程式必須要在 GUI 作業系統上運行，效能容易被 GUI 拖慢。
**解決方法：** 改調用 Shell 來運行 comman
　　
### OMXPlayer 播放延遲 ###

OMXPlayer 播放時約有1秒鐘的延遲

### Aplay 格式不支援 ###

Aplay再撥放wav時會產生雜訊  
**解決方法：** 音效（\*.wav）以 Aplay 播放；音樂（\*.mp3 or \*.wav）以 OMXPlayer 播放  
　　
## 運用哪些與課程內容中相關的技巧 ##

top, kill, ps 等針對 Process 監看/管理指令。

## 組裝過程及製作教學（GPIO線材的安裝、3D列印後的組裝...）##

  1. 鍵盤、音源、螢幕直接由 Raspberry Pi 的外部介面進行連接  

  2. 設定 Raspbian Config（音源以 3.5mm 輸出）

    - 於 `raspi-config` 內進行設定
    參考 [官方文件](https://www.raspberrypi.org/documentation/configuration/audio-config.md)

  3. 設置HDMI轉VGA轉接器  

    - 於 `/boot/config.txt` 內進行設定
    參考網站 [學習樹莓派－1.7 HDMI 轉 VGA](https://sites.google.com/site/raspberypishare0918/home/di-yi-ci-qi-dong/1-7-hdmi-zhuan-vga)

  4. 將本程式原始碼與 `JLine-1.0.jar` 進行編譯

    ```sh
    javac -cp .:jline-1.0.jar ./tw/edu/ncnu/lsa/ShellDJ.java  
    ```

  5. 音樂檔命名後放置本目錄(.)

    - 音效檔格式為（\*.wav） -> sound[97\~122].wav  
    - 音樂檔格式為（\*.mp3 or \*.wav） -> music[65\~90].mp3 or music[65\~90].wav  
    - 數字對應 Ascii 碼 65\~90(A\~Z) 97\~122(a\~z)  

  6. 執行本程式

    ```sh
    java -cp .:jline-1.0.jar ./tw/edu/ncnu/lsa/ShellDJ  
    ```

## 操作教學（做出此產品之後該如何操作）##  

**出現 Ready 後即可進行操作**  
  - **? 鍵：** 顯示可以輸入的聲音/音效按鍵  
  - **A\~Z：** 播放/停止對應音樂，第一次:播放 第二次:停止  
  - **a\~z：** 播放對應音樂/音效  

## 工作分配 ##  

  - 題目發想：哲瑋、騰右、曉柔  
  - 程式撰寫：哲瑋  
  - 系統展示：騰右  
  - 投影片製作：曉柔、哲瑋  

## 參考來源 ##  

  - Playing audio on the Raspberry Pi
    由 [Raspberry Pi Foundation](https://www.raspberrypi.org/) 以 [CC BY-SA](https://www.raspberrypi.org/creative-commons/) 方式授權[提供](https://www.raspberrypi.org/documentation/usage/audio/)
