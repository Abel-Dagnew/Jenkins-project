const path = require('path'); // Import path module for absolute paths

var config = {
  mode: 'development', // Set mode to development or production
  // Entry point for the application
  entry: './main.js',

  // Output configuration (output.path must be absolute)
  output: {
    path: path.resolve(__dirname, 'dist'), // Using an absolute path for the output directory
    filename: 'index.js'
  },
  

  // DevServer configuration
  devServer: {
    client: {
      logging: 'info', // Can be 'none', 'error', 'warn', or 'info' (replaces inline)
      overlay: true // Shows error messages in the browser overlay
    },
    port: 8888, // Specify the port
    liveReload: true, // Enable live reloading
    static: path.resolve(__dirname, 'dist') // Directory for static files
  },

  // Module configuration with rules (previously loaders)
  module: {
    rules: [
      {
        test: /\.jsx?$/, // Regex to match .js or .jsx files
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader', // Use the correct loader name
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react'] // Updated Babel presets
          }
        }
      }
    ]
  }
};

module.exports = config;
