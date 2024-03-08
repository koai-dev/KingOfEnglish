// Top-level build file where you can add configuration options common to all sub-projects/modules.
import groovy.util.XmlNodePrinter
import groovy.util.XmlParser
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

task("convertDimens") {
    val rootPath = project.rootDir.toString() + "/app/src/main/res/values"
    val dimenFileName = "dimens.xml"
    val defaultFilePath = "$rootPath/$dimenFileName"
    val sw360Path = "$rootPath-sw360dp/$dimenFileName"
    val sw480Path = "$rootPath-sw480dp/$dimenFileName"
    val sw540Path = "$rootPath-sw540dp/$dimenFileName"
    val sw600Path = "$rootPath-sw600dp/$dimenFileName"
    val sw640Path = "$rootPath-sw640dp/$dimenFileName"
    val sw720Path = "$rootPath-sw720dp/$dimenFileName"
    val sw800Path = "$rootPath-sw800dp/$dimenFileName"


    makeFolder(sw360Path, sw480Path, sw540Path, sw600Path, sw640Path, sw720Path, sw800Path)

    createOrCloneDefaultData(defaultFilePath, sw360Path)

    val xmlOriginal = XmlParser().parse(sw360Path)

    val xml480 = xmlOriginal.clone() as groovy.util.Node
    val xml540 = xmlOriginal.clone() as groovy.util.Node
    val xml600 = xmlOriginal.clone() as groovy.util.Node
    val xml640 = xmlOriginal.clone() as groovy.util.Node
    val xml720 = xmlOriginal.clone() as groovy.util.Node
    val xml800 = xmlOriginal.clone() as groovy.util.Node
    xml480.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((480f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((480f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else {
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((480f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")
        }

    }
    xml540.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((540f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((540f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else {
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((540f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")
        }

    }
    xml600.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((600f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((600f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else {
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((600f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")

        }
    }
    xml640.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((640f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((640f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else {
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((640f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")
        }
    }

    xml720.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((720f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((720f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else{
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((720f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")
        }
    }
    xml800.children().forEach { node ->
        val dimen = node as groovy.util.Node
        val value = (dimen.value() as List<*>)[0].toString()

        if (value.contains("px")) {
            var newValue = value.replace("px", "").toFloat()
            newValue = round((800f / 360 * newValue), 1)
            dimen.setValue("${newValue}px")
        } else if (value.contains("sp")) { // Xử lý giá trị chữ (sp)
            var newValue = value.replace("sp", "").toFloat()
            newValue = round((800f / 360 * newValue), 1) // Chuyển đổi cho kích thước chữ (sp)
            dimen.setValue("${newValue}sp")
        } else{
            var newValue = value.replace("dp", "").toFloat()
            newValue = round((800f / 360 * newValue), 1)
            dimen.setValue("${newValue}dp")
        }

    }
    XmlNodePrinter(PrintWriter(FileWriter(sw480Path))).print(xml480)
    XmlNodePrinter(PrintWriter(FileWriter(sw540Path))).print(xml540)
    XmlNodePrinter(PrintWriter(FileWriter(sw600Path))).print(xml600)
    XmlNodePrinter(PrintWriter(FileWriter(sw640Path))).print(xml640)
    XmlNodePrinter(PrintWriter(FileWriter(sw720Path))).print(xml720)
    XmlNodePrinter(PrintWriter(FileWriter(sw800Path))).print(xml800)
}


fun createOrCloneDefaultData(defaultPath: String, targetPath: String) {
    val defaultFile = File(defaultPath)
    if (!defaultFile.exists()) {
        defaultFile.createNewFile()
        val content = StringBuilder("<resources>\n")
        for (i in 2..100 step 2) {
            content.append("\t<dimen name=\"dp$i\">${i}dp</dimen>\n")
        }
        content.append("</resources>")
        val bw = BufferedWriter(FileWriter(defaultPath))
        bw.write(content.toString())
        bw.close()
    }

    val targetFile = File(targetPath)
    if (defaultFile.exists()) {
        copyFileUsingStream(defaultFile, targetFile)
    }
}

@Throws(IOException::class)
fun copyFileUsingStream(source: File, dest: File) {
    var inputStream: FileInputStream? = null
    var outputStream: FileOutputStream? = null
    try {
        inputStream = FileInputStream(source)
        outputStream = FileOutputStream(dest)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
    } finally {
        inputStream?.close()
        outputStream?.close()
    }
}

fun round(value: Float, decimalPlace: Int): Float {
    val bd = BigDecimal(value.toString()).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bd.toFloat()
}

fun makeFolder(vararg paths: String) {
    for (path in paths) {
        val folder = File(File(path).parent)
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }
}