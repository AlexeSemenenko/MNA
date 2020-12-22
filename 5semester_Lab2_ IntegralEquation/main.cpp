#include <iostream>
#include <cmath>
#include <vector>

using namespace std;

void fillData(vector<double>& x, vector<double>& f, vector<vector<double>>& K, int n, double a, double h);
void fredholmMQ(vector<double>& f, vector<vector<double>>& K, vector<double>& y, double L, double h, int n);
void fredholmSA(vector<double>& f, vector<vector<double>>& K, vector<double>& yn1, vector<double>& yn, double L, double h, int n);
void volterMQ(vector<double>& f, vector<vector<double>>& K, vector<double>& y, double h, double L, int n);
void volterSA(vector<double>& f, vector<vector<double>>& K, vector<double>& yk1, vector<double>& yk, double L, double h, int n);
double fredholmPointValue(double x0, vector<double>& x, vector<double>& y, double h, double L, int n);
double volterPointValue(double x0, vector<double>& x, vector<double>& y, double h, double L, int n);
void print(vector<double>& y, double fx);

int main()
{
    double a = 0.0;
    double b = 1.0;
    double L = 0.35;
    int n;
    double h;
    double x0 = (a + b) / 2.2;
    double fx0;
    vector<double> x;
    vector<double> f;
    vector<vector<double>> K;
    vector<double> yn;
    vector<double> yn1;


    //Механические квадратуры Фредгольм и вычисление значения в точке
    n = 10;
    h = (b - a) / n;
    yn.clear();

    fillData(x, f, K, n, a, h);
    fredholmMQ(f, K, yn, L, h, n);
    fx0 = fredholmPointValue(x0, x, yn, h, L, n);
    cout <<"Fredholm MQ:" << endl;
    print(yn, fx0);


    //Механические квадратуры Вольтер и вычисление значения в точке
    n = 10;
    h = (b - a) / n;
    yn.clear();

    fillData(x, f, K, n, a, h);
    volterMQ(f, K, yn, h, L, n);
    fx0 = volterPointValue(x0, x, yn, h, L, n);
    cout << endl << "Volter MQ:" << endl;
    print(yn, fx0);


    //Метод последовательных приближений Фредгольм и вычисление значения в точке
    n = 3;
    h = (b - a) / n;
    yn.clear();
    yn1.clear();

    fillData(x, f, K, n, a, h);
    fredholmSA(f, K, yn1, yn, L, h, n);
    fx0 = fredholmPointValue(x0, x, yn, h, L, n);
    cout << endl << "Fredholm SA:" << endl;
    print(yn, fx0);


    //Метод последовательных приближений Вольтер и вычисление значения в точке
    n = 3;
    h = (b - a) / n;
    yn.clear();
    yn1.clear();

    fillData(x, f, K, n, a, h);
    volterSA(f, K, yn1, yn, L, h, n);
    fx0 = volterPointValue(x0, x, yn, h, L, n);
    cout << endl << "Volter SA:" << endl;
    print(yn, fx0);

    return 0;
}

void fillData(vector<double>& x, vector<double>& f, vector<vector<double>>& K, int n, double a, double h) {

    x.clear();
    f.clear();
    K.clear();

    K.resize(n);

    for (int i = 0; i < n; i++) {
        x.push_back(a + i * h);
        f.push_back(sinh(x[i]));
    }

    for (int i = 0; i < n; i++) {
        for (int k = 0; k < n; k++) {
            K[i].push_back(1 + exp(- x[i] * x[k]));
        }
    }
}

void fredholmMQ(vector<double>& f, vector<vector<double>>& K, vector<double>& y, double L, double h, int n) {

    vector<vector<double>> matr;
    double matr_i_i;
    double matr_q_i;
    double yi;

    matr.resize(n);
    for (int i = 0; i <= n-1; i++) {

        for (int k = 0; k <= n-1; k++) {

            if (i == k) {
                matr[i].push_back(1 - L * h * K[i][k]);
            }
            else {
                matr[i].push_back(- L * h * K[i][k]);
            }
        }
    }

    for (int i = 0; i <= n - 1; i++) {

        matr_i_i = matr[i][i];
        for (int k = 0; k < n; k++) {
            matr[i][k] /= matr_i_i;
        }
        f[i] /= matr_i_i;

        for (int q = i + 1; q <= n - 1; q++) {

            matr_q_i = matr[q][i];
            for (int k = i; k < f.size(); k++) {
                matr[q][k] -= matr_q_i * matr[i][k];
            }
            f[q] -= matr_q_i * f[i];
        }
    }

    y.resize(n);
    for (int i = n - 1; i >= 0; i--) {

        y[i] = f[i];
        for (int k = i + 1; k <= n - 1; k++) {
            y[i] -= y[k] * matr[i][k];
        }
    }
}

void fredholmSA(vector<double>& f, vector<vector<double>>& K, vector<double>& yn1, vector<double>& yn, double L, double h, int n) {

    yn.insert(yn.cend(), f.cbegin(), f.cend());

    for (int k = 1; k <= n - 1; k++) {

        for (int i = 0; i <= n - 1; i++) {

            yn1.push_back(0);
            for (int j = 0; j <= n - 1; j++) {
                yn1[i] += K[i][j] * yn[j];
            }
            yn1[i] *= h * L;
            yn1[i] += f[i];
        }

        yn.clear();
        yn.insert(yn.cend(), yn1.cbegin(), yn1.cend());
        yn1.clear();
    }
}

double fredholmPointValue(double x0, vector<double>& x, vector<double>& y, double h, double L, int n) {

    double fx;

    fx = sinh(x0);
    for (int k = 0; k <= n - 1; k++) {
        fx += (1 + exp(- x0 * x[k])) * y[k];
    }
    fx *= h * L;

    return fx;
}

void volterMQ(vector<double>& f, vector<vector<double>>& K, vector<double>& y, double h, double L, int n) {

    double yi;

    for (int i = 0; i <= n - 1; i++) {

        yi = f[i];
        for (int j = 0; j < i; j++) {
            yi += L * h * K[i][j] * y[j];
        }
        y.push_back(yi / (1 - L * h * K[i][i]));
    }
}

void volterSA(vector<double>& f, vector<vector<double>>& K, vector<double>& yk1, vector<double>& yk, double L, double h, int n) {

    yk.insert(yk.cend(), f.cbegin(), f.cend());

    for (int k = 1; k <= n - 1; k++) {

        for (int i = 0; i <= n - 1; i++) {

            yk1.push_back(0);
            for (int j = 0; j <= i; j++) {
                yk1[i] += K[i][j] * yk[j];
            }
            yk1[i] *= h * L;
            yk1[i] += f[i];
        }

        yk.clear();
        yk.insert(yk.cend(), yk1.cbegin(), yk1.cend());
        yk1.clear();
    }
}

double volterPointValue(double x0, vector<double>& x, vector<double>& y, double h, double L, int n) {

    double fx;

    fx = sinh(x0);
    for (int k = 0; x[k] <= x0; k++) {
        fx += (1 + exp(- x0 * x[k])) * y[k];
    }
    fx *= h * L;

    return fx;
}

void print(vector<double>& y, double fx) {

    cout << "y: (";
    for (int i = 0; i < y.size() - 1; i++) {
        cout << y[i] << ", ";
    }
    cout << y[y.size() - 1] << ")\n";

    cout << "Value at x*: " << fx << "\n";
}

