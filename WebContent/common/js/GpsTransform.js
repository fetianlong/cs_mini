var a = 6378245.0;
var ee = 0.00669342162296594323;
var pi = 3.14159265358979324;
var x_pi = 3.14159265358979324 * 3000.0 / 180.0;


function tx2bd(lat,lon){
	var result = new Object();
    var x = lon, y = lat;
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
    result.lng = (z * Math.cos(theta) + 0.0065);
    result.lat = (z * Math.sin(theta) + 0.006);
    return result;
}

function transPoint( lat, lng){
	alert(lat+";"+lng);
	var result = transform(lat, lng);
	var latstr = result.lat.toString();
	var lngstr = result.lng.toString();
	latstr = latstr.substring(0, 8);
	lngstr = lngstr.substring(0, 8);
	alert(latstr+";"+lngstr);
	result = tx2bd(Number(latstr), Number(lngstr));
	alert(result.lng+";"+result.lat);
	return result;
}

/// <summary>
/// GPS转google坐标进行转换
/// </summary>
/// <param name="wgLat"></param>
/// <param name="wgLon"></param>
/// <param name="mgLat"></param>
/// <param name="mgLon"></param>
function transform(wgLat, wgLon)
{
	var result = new Object();
    var dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
    var dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
    var radLat = wgLat / 180.0 * pi;
    var magic = Math.sin(radLat);
    magic = 1 - ee * magic * magic;
    var sqrtMagic = Math.sqrt(magic);
    dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
    dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
    result.lat = (wgLat + dLat);
    result.lng = (wgLon + dLon);
    return result;
}

/// <summary>
/// gps坐标转谷歌坐标
/// </summary>
/// <param name="x"></param>
/// <param name="y"></param>
/// <returns></returns>
function transformLat(x, y)
{
    var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
    ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
    return ret;
}
/// <summary>
/// gps坐标转谷歌坐标
/// </summary>
/// <param name="x"></param>
/// <param name="y"></param>
/// <returns></returns>
function transformLon(x, y)
{
    var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
    ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
    return ret;
}


