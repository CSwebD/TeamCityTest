const path = require('path');

module.exports = {
  mode: 'production',
  entry: './app.js', // Replace with the main file of your app
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
  },
};
