using System.Windows;

namespace LinkBridge.Windows.Views
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void PairButton_Click(object sender, RoutedEventArgs e)
        {
            StatusText.Text = "Waiting for phone to scan QR code...";
            
            using (var qrGenerator = new QRCoder.QRCodeGenerator())
            using (var qrCodeData = qrGenerator.CreateQrCode("LinkBridge:Pairing:Test", QRCoder.QRCodeGenerator.ECCLevel.Q))
            {
                var qrCode = new QRCoder.Xaml.XamlQRCode(qrCodeData);
                var qrCodeImage = qrCode.GetGraphic(20);
                QrCodeImage.Source = qrCodeImage;
                QrCodeImage.Visibility = Visibility.Visible;
            }
        }
    }
}
