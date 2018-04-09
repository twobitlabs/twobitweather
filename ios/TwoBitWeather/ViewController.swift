import UIKit
import TwoBitLabs

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        TBLWeatherWebService().getForecast { (forecast) -> TBLStdlibUnit in
            print("XXXX")
            return TBLStdlibUnit()
        }
    }
}

