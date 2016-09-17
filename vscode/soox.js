// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
var vscode = require('vscode');
// this method is called when your extension is activated
// your extension is activated the very first time the command is executed
function activate(context) {

    // Use the console to output diagnostic information (console.log) and errors (console.error)
    // This line of code will only be executed once when your extension is activated
    console.log('Congratulations, your extension "soox" is now active!');

    // The command has been defined in the package.json file
    // Now provide the implementation of the command with  registerCommand
    // The commandId parameter must match the command field in package.json
    var disposable = vscode.commands.registerCommand('soox.search', function () {
        // The code you place here will be executed every time your command is executed
        //let uri = Uri.parse('file:///C:\Users\smark\so\test\index.js');
        // let success = await commands.executeCommand('vscode.previewHtml', uri);
        // Display a message box to the user
        var sokey = vscode.window.showInputBox({
            prompt: "输入要搜索的关键词通过suffix指定搜索引擎",
            placeHolder: "'vscode g'"
        });
        sokey.then(function (str) {
            console.log(str)
            search(str);
        })
    });

    var cmd =vscode.commands.registerCommand("soox.cmd",()=>{
        var process = require("child_process")
        process.exec("start cmd")
    })
    context.subscriptions.push(cmd)
    context.subscriptions.push(disposable);
}
exports.activate = activate;

// this method is called when your extension is deactivated
function deactivate() {
    
}
exports.deactivate = deactivate;

function search(str) {
    var keywords = "";
    var engin;
    var oscmd;
    var url;
    if (str == undefined) {
        return;
    }
    str = str.trim()
    if (str.trim().length > 0) {
        var ks = str.split(" ");
        keywords = ks.slice(ks,ks.length-1).join("+")
        engin = str.substring(str.length - 1);
        var browser = require("child_process")
        url = enginPrefix(engin) + keywords;
        browser.exec("start " + url)
    }


}

function enginPrefix(tag) {
    var uriPre = "http://"
    //vscode.workspace.getConfiguration("so.suffix.baidu")
    switch (tag) {
        case "g": uriPre = "https://www.google.com/#q="; break;
        case "d": uriPre = "http://www.baidu.com/s?wd="; break;
        case "b": uriPre = "http://www.bing.com/search?q="; break;
        case "3": uriPre = "https://www.so.com/s?q="; break;
        case "s": uriPre = "http://stackoverflow.com/search?q="; break;
        case "h": uriPre = "https://github.com/search?q="; break;
        case "w": uriPre = "http://www.wolframalpha.com/input/?i=";break;
        default:
            uriPre = "https://www.google.com/#q=";
    }
    return uriPre;
}