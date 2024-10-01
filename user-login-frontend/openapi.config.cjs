// 感谢鱼皮大佬的代码
// https://github.com/liyupi/yudada/blob/master/yudada-frontend/openapi.config.ts

const { generateService } = require("@umijs/openapi");

generateService({
  requestLibPath: "import request from '@/request.ts'",
  schemaPath: "http://localhost:8081/v2/api-docs",
  serversPath: "./src",
}).then(() => {

// 清除方法名中 UsingXxx 部分
const fs = require('fs');
const path = require('path');

// 读取生成的文件
const generatedFilesPath = path.join(__dirname, './src/api');
const files = fs.readdirSync(generatedFilesPath);

files.forEach(file => {
  const filePath = path.join(generatedFilesPath, file);
  if (fs.statSync(filePath).isFile() && path.extname(file) === '.ts') {
    let fileContent = fs.readFileSync(filePath, 'utf-8');

    // 替换方法名中的 UsingXxx 部分
    fileContent = fileContent.replace(/export async function (\w+)Using(\w+)/g, 'export async function $1');

    // 保存文件
    fs.writeFileSync(filePath, fileContent, 'utf-8');
  }
});

console.log("清除方法名中 UsingXxx 部分");

});