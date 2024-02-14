module.exports = {
  devServer: {
    proxy:{
      '/animate': {
        target: 'http://localhost:9090',
      }
    }
  }
}
