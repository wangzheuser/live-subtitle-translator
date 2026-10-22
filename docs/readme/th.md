<div align="center">
  <img src="../../src-tauri/icons/128x128@2x.png" width="96" alt="mimi">
  <h1>Mimi</h1>
  <p>คำบรรยายแปลสดจากเสียงคอมพิวเตอร์หรือไมโครโฟน</p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/github/v/release/yuxino/mimi?style=flat&amp;logo=github&amp;logoColor=white" alt="รุ่นล่าสุด"></a>
    <a href="https://github.com/yuxino/mimi/releases"><img src="https://img.shields.io/github/downloads/yuxino/mimi/total?style=flat&amp;labelColor=a85f82&amp;color=e889b5" alt="ยอดดาวน์โหลดทั้งหมด"></a>
    <a href="https://github.com/yuxino/mimi/actions/workflows/ci.yml?query=branch%3Amain"><img src="https://img.shields.io/github/actions/workflow/status/yuxino/mimi/ci.yml?style=flat&amp;logo=githubactions&amp;logoColor=white&amp;branch=main&amp;event=push&amp;label=CI" alt="สถานะ CI บน main"></a>
    <a href="../../LICENSE"><img src="https://img.shields.io/github/license/yuxino/mimi?style=flat&amp;logo=opensourceinitiative&amp;logoColor=white" alt="สัญญาอนุญาต MIT"></a>
  </p>
  <p>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/macOS-13%2B-555?style=flat&amp;logo=apple&amp;logoColor=white" alt="macOS 13+"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Windows-x64-0078D4?style=flat&amp;logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI%2BPHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0wIDBoMTF2MTFIMHptMTMgMGgxMXYxMUgxM3pNMCAxM2gxMXYxMUgwem0xMyAwaDExdjExSDEzeiIvPjwvc3ZnPg%3D%3D&amp;logoColor=white" alt="Windows x64"></a>
    <a href="https://github.com/yuxino/mimi/releases/latest"><img src="https://img.shields.io/badge/Linux-x86__64-FCC624?style=flat&amp;logo=linux&amp;logoColor=white" alt="Linux"></a>
    <a href="../../android/README.md"><img src="https://img.shields.io/badge/Android-app-3DDC84?style=flat&amp;logo=android&amp;logoColor=white" alt="Android"></a>
  </p>
  <p>
    <a href="../../README_EN.md">English</a> · <a href="../../README.md">简体中文</a> · <a href="zh-TW.md">繁體中文</a> · <a href="ja.md">日本語</a> · <a href="th.md"><strong>ภาษาไทย</strong></a> · <a href="ko.md">한국어</a> · <a href="fr.md">Français</a> · <a href="de.md">Deutsch</a>
  </p>
</div>

![ภาพตัวอย่างคำบรรยายสองภาษาของ Mimi พร้อมบทสนทนาสมมติ](../assets/readme-preview.png)

ภาพจำลองหน้าจอพร้อมบทสนทนาตัวอย่าง [ดูคำบรรยายและโหมดเต็มอารมณ์ (หน้าตาเวอร์ชันเก่า)](../demos/README.md)

## ฟังไป อ่านคำบรรยายไป

- **เข้าใจสิ่งที่กำลังเล่น**: แสดงคำพูดในภาพยนตร์ ไลฟ์ และบทเรียนเป็นคำบรรยายลอย เลือกสองภาษา เฉพาะคำแปล หรือเฉพาะต้นฉบับได้
- **เลือกเสียงที่ต้องการ**: ค่าเริ่มต้นคือเสียงคอมพิวเตอร์ เปิดไมโครโฟนหรือทั้งสองแหล่งเองได้ และเลือกแอปเฉพาะบน macOS / Windows 11 ได้ บน Linux เลือกสตรีมการเล่นเสียงแต่ละรายการได้
- **ปรับให้อ่านสบาย**: บนเดสก์ท็อป เปลี่ยนตำแหน่ง ขนาดตัวอักษร 14–48 px และสีได้ เลือกแสดงคำบรรยายล่าสุดทั้งหมดหรือเพียง 1–10 รายการ และเลื่อนขึ้นเพื่ออ่านประวัติที่ยังเก็บอยู่ โหมดเต็มอารมณ์เหลือแค่ข้อความ และการคลิกทะลุช่วยให้ใช้แอปด้านล่างต่อได้
- **บันทึกคำบรรยายและเสียง**: บนเดสก์ท็อป ส่งออกคำบรรยายเป็น TXT หรือ Markdown หรืออัดเสียงจากแหล่งที่เลือกเป็น WAV ได้ ต้องเปิดใช้การบันทึกทั้งสองอย่างด้วยตนเอง

## เริ่มใช้งาน

[ดาวน์โหลดรุ่นเดสก์ท็อป](https://github.com/yuxino/mimi/releases/latest) · [ติดตั้งและใช้งานบน Android (ภาษาอังกฤษ)](../../android/README.md#start)

รุ่นเดสก์ท็อปรองรับ macOS 13+ (ชิป Apple และ Intel), Windows x64 และ Linux x86_64

1. **ตั้งค่าบริการ**: เพิ่มบริการเสียงใน การตั้งค่า → โมเดลและบริการ ดูวิธีเลือกบริการและรับคีย์ได้ใน[คู่มือตั้งค่า (ภาษาอังกฤษ)](../provider-setup.md#choose)
2. **เลือกเสียงและภาษา**: ในการตั้งค่าเสียงและภาษา เลือกแหล่งเสียง ภาษาที่พูด และภาษาแปล
3. **เริ่มคำบรรยาย**: เปิดคำบรรยายสดในหน้าคำบรรยาย อนุญาตสิทธิ์ที่ระบบขอ แล้วเล่นเนื้อหาที่ต้องการ

Mimi ใช้ฟรี แต่บริการคลาวด์อาจมีค่าใช้จ่าย การรู้จำหรือแปลผ่านคลาวด์จะส่งเสียงหรือข้อความที่รู้จำได้ไปยังบริการที่เลือก ดูเงื่อนไขของบริการ Apple ที่ประมวลผลในเครื่องได้ใน[คู่มือตั้งค่า (ภาษาอังกฤษ)](../provider-setup.md#apple-speech)

## ต้องการความช่วยเหลือ?

[คู่มือการใช้งาน](../usage.th.md) ครอบคลุมการติดตั้ง สิทธิ์ การแสดงคำบรรยาย และการบันทึก หากไม่มีคำบรรยายหรือเชื่อมต่อไม่ได้ ให้ดู[ข้อผิดพลาดในการรู้จำเสียง](../usage.th.md#errors) ก่อน หากยังไม่หาย [แจ้งปัญหา](https://github.com/yuxino/mimi/issues)ได้

## ผู้ร่วมพัฒนา

หากอยากช่วยพัฒนา เริ่มที่[คู่มือร่วมพัฒนา (อังกฤษ/จีน)](../../.github/CONTRIBUTING.md)

ขอบคุณทุกคนที่เขียนโค้ด รายงานปัญหา ทดลองใช้ Mimi หรือแบ่งปันให้ผู้อื่น (๑•̀ㅂ•́)و✧

ขอบคุณเป็นพิเศษแก่ [@yebuwudong](https://github.com/yebuwudong) สำหรับ [แอป Android](https://github.com/yuxino/mimi/pull/37) และ [@LLLin000](https://github.com/LLLin000) สำหรับ [แอนิเมชันคำบรรยาย](https://github.com/yuxino/mimi/pull/67) และ [การปรับปรุงเสียงบน Windows](https://github.com/yuxino/mimi/pull/89)

ขอบคุณ [@Chtholly000](https://github.com/Chtholly000) ที่ปรับปรุง [คำบรรยายต่อเนื่องของ Gemini และการเปลี่ยนการเชื่อมต่อตามกำหนด](https://github.com/yuxino/Mimi/pull/176)

ขอบคุณ [@EmmetZ](https://github.com/EmmetZ) สำหรับการ[แก้ไขตัวควบคุมคำบรรยายบน Wayland](https://github.com/yuxino/Mimi/pull/242) และเพิ่ม[การจับเสียงจากสตรีมการเล่นแต่ละรายการบน Linux](https://github.com/yuxino/Mimi/pull/275) พร้อม[ไอคอนแอปสำหรับสตรีมการเล่น](https://github.com/yuxino/Mimi/pull/304)

ขอบคุณ [@wangmian0](https://github.com/wangmian0) ที่ [ปิดโหมดคิดของ DeepSeek สำหรับการแปลคำบรรยายสด](https://github.com/yuxino/Mimi/pull/270)

ขอบคุณ [@hubumulity](https://github.com/hubumulity) สำหรับ[การตั้งค่าจำนวนคำบรรยายที่แสดง](https://github.com/yuxino/Mimi/pull/303)

ขอบคุณ [@ryannyui](https://github.com/ryannyui) สำหรับ[การแก้ไขความเข้ากันได้ของกราฟิกใน Linux AppImage และการอัปเกรดชุดเครื่องมือ](https://github.com/yuxino/Mimi/pull/312)

<p>
  <a href="https://github.com/yuxino"><img src="../assets/contributors/yuxino.svg" width="40" height="40" alt="@yuxino"></a>
  <a href="https://github.com/LLLin000"><img src="../assets/contributors/LLLin000.svg" width="40" height="40" alt="@LLLin000"></a>
  <a href="https://github.com/yebuwudong"><img src="../assets/contributors/yebuwudong.svg" width="40" height="40" alt="@yebuwudong"></a>
  <a href="https://github.com/Chtholly000"><img src="../assets/contributors/Chtholly000.svg" width="40" height="40" alt="@Chtholly000"></a>
  <a href="https://github.com/inhome"><img src="../assets/contributors/inhome.svg" width="40" height="40" alt="@inhome"></a>
  <a href="https://github.com/EmmetZ"><img src="../assets/contributors/EmmetZ.svg" width="40" height="40" alt="@EmmetZ"></a>
  <a href="https://github.com/wangmian0"><img src="../assets/contributors/wangmian0.svg" width="40" height="40" alt="@wangmian0"></a>
  <a href="https://github.com/hubumulity"><img src="../assets/contributors/hubumulity.svg" width="40" height="40" alt="@hubumulity"></a>
  <a href="https://github.com/ryannyui"><img src="../assets/contributors/ryannyui.svg" width="40" height="40" alt="@ryannyui"></a>
</p>

[ผู้ร่วมพัฒนาทั้งหมด](https://github.com/yuxino/mimi/graphs/contributors)

## ชุมชน

ขอบคุณผู้ใช้จาก [V2EX](https://www.v2ex.com/), [LINUX DO](https://linux.do/), [Appinn](https://meta.appinn.net/), [NodeLoc](https://www.nodeloc.com/), [Solo](https://solo.xin/), [Xinquji](https://xinquji.com/posts/859305) และ [Eleduck](https://eleduck.com/) ที่ทดลองใช้ Mimi แบ่งปันความคิดเห็น และช่วยบอกต่อ

[MIT](../../LICENSE) © 2026 yuxino
