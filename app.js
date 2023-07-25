const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser')
const ProductModel = require('./model');
const uri = "mongodb+srv://tungktph27526:kttung2609@cluster0.mdas35v.mongodb.net/assmandroidnetwork?retryWrites=true&w=majority";

mongoose.connect(uri,{
    useNewUrlParser: true,
    useUnifiedTopology: true,
}) 
.then(() => {
    console.log('Da ket noi voi MongoDB');
})
.catch((err) => {
    console.error('Khong ket noi dc MongoDB: ', err);
})

const app = express();
app.use(bodyParser.json());

app.get('/list', async function (req, res) {

    try {
        const productList = await ProductModel.find().lean();
        res.json(productList);
      } catch (err) {
        console.error('Error fetching products:', err);
        res.status(500).json({ error: 'Internal server error' });
      }
})

// Định nghĩa API thêm sản phẩm
app.post('/addProduct', async (req, res) => {
    try {
      const { name, price, description} = req.body;
      const product = new ProductModel({ name, price, description });
      await product.save();
      showList(res);
    } catch (err) {
      console.error('Error adding product:', err);
      res.status(500).json({ error: 'Internal server error' });
    }
  });
  
  // Định nghĩa API sửa đổi sản phẩm
  app.put('/product/:id', async (req, res) => {
    try {
      const { id } = req.params;
      const { name, price, description } = req.body;
      const product = await ProductModel.findByIdAndUpdate(
        id,
        { name, price, description },
        { new: true }
      );
      showList(res);
    } catch (err) {
      console.error('Error updating product:', err);
      res.status(500).json({ error: 'Internal server error' });
    }
  });
  
  // Định nghĩa API xóa sản phẩm
  app.delete('/product/:id', async (req, res) => {
    try {
      const { id } = req.params;
      await ProductModel.findByIdAndRemove(id);
      // res.json({ success: true });
      showList(res);
    } catch (err) {
      console.error('Error deleting product:', err);
      res.status(500).json({ error: 'Internal server error' });
    }
  });
  async function showList(res){
    const productList = await ProductModel.find().lean();
    res.json(productList);
  }
  
  // Khởi động server
  const port = 8080;
  app.listen(port, () => {
    console.log(`Server started on port ${port}`);
  });
  